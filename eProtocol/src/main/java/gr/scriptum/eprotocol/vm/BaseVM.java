/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.InputElement;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.ProtocolCorrespondentDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.eprotocol.security.ScriptumUser;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public abstract class BaseVM {

	private static final Class<?>[] inputElements = { Bandbox.class, Combobox.class, Datebox.class, Decimalbox.class,
			Doublebox.class, Intbox.class, Longbox.class, Spinner.class, Textbox.class, Timebox.class };

	private static Log log = LogFactory.getLog(BaseVM.class);

	public static final Integer PAGE_SIZE_DEFAULT = 10;

	public static final Integer PAGE_SIZE_LARGE = 10;

	/***
	 * Given a component, recursively traverses the child component hierarchy,
	 * forcing validation on all input type components (Textboxes, Dateboxes
	 * etc.). Adds all validation errors found to an exception list.
	 * 
	 * @param root
	 *            The component to start traversing from
	 * @param errors
	 *            The list of errors found so far
	 * @param component
	 *            if present, specific components to validate, all
	 *            direct/indirect children of the root component
	 * @return A list of zk validation exceptions
	 */
	private List<WrongValueException> validateFields(Component root, List<WrongValueException> errors,
			Component... component) {

		List<Component> children = root.getChildren();

		for (Component child : children) {

			if (ArrayUtils.contains(inputElements, child.getClass())) {
				Method method = null;
				try {
					method = child.getClass().getMethod("getValue", null);
				} catch (Exception e) {
					log.error(e);
				}

				if (method != null) {
					try {
						method.invoke(child, new Object[] {});
					} catch (InvocationTargetException e) {
						// log.warn(e.getCause());
						if (e.getCause() instanceof WrongValueException) {
							if (component.length != 0) {
								for (Component comp : component) {
									if (child.equals(comp)) {
										errors.add((WrongValueException) e.getCause());
										break;
									}
								}
							} else {
								errors.add((WrongValueException) e.getCause());
							}
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
			}

			validateFields((Component) child, errors, component);
		}
		return errors;
	}

	/**
	 * Given a root component, recursively clears all validation messages on any
	 * InputElement child components.
	 * 
	 * @param root
	 *            The component to start traversing from
	 */
	protected void clearValidationMessages(Component root) {

		List<Component> children = root.getChildren();

		for (Component child : children) {
			if (child instanceof InputElement) {
				((InputElement) child).clearErrorMessage(true);
			}

			clearValidationMessages(child);
		}
	}

	/**
	 * Given a root component, recursively traverses the child component
	 * hierarchy, forcing validation on all input type components (Textboxes,
	 * Dateboxes etc). When finished, throws a WrongValuesException, containing
	 * all WrongValueException instances thrown by components failing
	 * validation.
	 * 
	 * @param root
	 *            The root component to begin traversing from
	 * @param component
	 *            if present, an explicit list of components to validate, all
	 *            direct/indirect children of the root component
	 */
	protected void validateFields(Component root, Component... component) {
		List<WrongValueException> errors = new ArrayList<WrongValueException>();
		errors = validateFields(root, errors, component);
		if (!errors.isEmpty()) {
			throw new WrongValuesException(errors.toArray(new WrongValueException[0]));
		}
	}

	protected OkmProtocolDispatcherImpl getOkmDispatcher() {
		return SpringUtil.getApplicationContext().getBean(ProtocolService.class).getOkmDispatcher();
	}

	protected List<String> getUserInSessionRoles() {
		List<String> roles = new ArrayList<String>();
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(grantedAuthority.getAuthority());
		}
		return roles;
	}

	protected Users getUserInSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof String) {
			return null;
		} else {
			ScriptumUser scriptumUser = (ScriptumUser) authentication.getPrincipal();
			return scriptumUser.getUser();
		}
	}

	protected Department getUserInSessionDepartment() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof String) {
			return null;
		} else {
			ScriptumUser scriptumUser = (ScriptumUser) principal;
			return scriptumUser.getDepartment();
		}
	}

	protected DepartmentType.Name getUserInSessionDepartmentType() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof String) {
			return null;
		} else {
			ScriptumUser scriptumUser = (ScriptumUser) principal;
			return scriptumUser.getDepartmentType();
		}
	}

	protected String getOkmToken() throws OpenKMException {
		return SpringUtil.getApplicationContext().getBean(ProtocolService.class).getOkmToken();
	}

	protected void clearOkmToken(String okmToken) throws OpenKMException {
		SpringUtil.getApplicationContext().getBean(ProtocolService.class).clearOkmToken(getUserInSession(), okmToken);
	}

	protected void setSortingListheader(Listheader listHeader) {
		String direction = null;
		if (listHeader.getSortDirection().equals("ascending")) {
			direction = "descending";
		} else if (listHeader.getSortDirection().equals("natural")) {
			direction = "ascending";
		} else if (listHeader.getSortDirection().equals("descending")) {
			direction = "ascending";
		}
		Listbox parentLstbx = (Listbox) listHeader.getParent().getParent();
		for (Object child : parentLstbx.getListhead().getChildren()) {
			Listheader header = (Listheader) child;
			header.setSortDirection(header != listHeader ? "natural" : direction);
		}
	}

	protected Listheader getSortingListheader(Listbox listbox) {
		for (Object child : listbox.getListhead().getChildren()) {
			Listheader header = (Listheader) child;
			if (!header.getSortDirection().equals("natural")) {
				return header;
			}
		}
		return null;
	}

	protected String getIp() {
		return Executions.getCurrent().getRemoteAddr();
	}

	protected List<Order> getSortBy(Listheader header) {
		String[] tokens = header.getValue().toString().split(IConstants.SORTING_DELIMITER);
		List<Order> sortBy = new LinkedList<Order>();
		for (String token : tokens) {
			Order order = null;
			if (header.getSortDirection().equals("ascending")) {
				order = Order.asc(token);
			} else {
				order = Order.desc(token);
			}
			sortBy.add(order);
		}
		return sortBy;
	}

	protected Object trimStringProperties(Object entity) {
		// trim all string properties to null
		List props = Arrays.asList(PropertyUtils.getPropertyDescriptors(entity));
		Iterator it = props.iterator();

		while (it.hasNext()) {
			PropertyDescriptor pd = (PropertyDescriptor) it.next();
			String propertyName = pd.getName();
			Object propertyValue;
			try {
				propertyValue = PropertyUtils.getProperty(entity, propertyName);

			} catch (Exception e) {
				log.warn(e);
				// property was not accessible - this should be safe to swallow
				// and continue
				continue;
			}
			if (propertyValue instanceof String) {
				try {
					PropertyUtils.setProperty(entity, propertyName, StringUtils.trimToNull((String) propertyValue));
				} catch (Exception e) {
					// property was not accessible - this should be safe to
					// swallow
					// and continue
					log.warn(e);
					continue;
				}
			}
		}
		return entity;
	}

	protected Object[] searchProtocolDepartments(String term, Integer startIndex, Integer pageSize,
			Department... excludedDepartments) {
		DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);

		// set up paging by counting records first
		Integer departmentsTotalSize = departmentDAO
				.countDepartmentsByTerm(term, DepartmentDAO.PROTOCOL_DEPARTMENT_TYPES, excludedDepartments)
				.intValue();

		List<Department> departments = departmentDAO.findDepartmentsByTerm(term,
				DepartmentDAO.PROTOCOL_DEPARTMENT_TYPES, startIndex, pageSize, excludedDepartments);
		return new Object[] { departmentsTotalSize, departments };
	}

	protected Object[] searchAllDepartments(String term, Integer startIndex, Integer pageSize,
			Department... excludedDepartments) {
		DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);

		// set up paging by counting records first
		Integer departmentsTotalSize = departmentDAO
				.countDepartmentsByTerm(term, DepartmentDAO.ALL_DEPARTMENT_TYPES, excludedDepartments)
				.intValue();

		List<Department> departments = departmentDAO.findDepartmentsByTerm(term,
				DepartmentDAO.ALL_DEPARTMENT_TYPES, startIndex, pageSize, excludedDepartments);
		return new Object[] { departmentsTotalSize, departments };
	}

	protected Object[] searchContacts(String term, Integer startIndex, Integer contactsPageSize) {
		ContactDAO contactDAO = SpringUtil.getApplicationContext().getBean(ContactDAO.class);

		// set up paging by counting records first
		Integer contactsTotalSize = contactDAO.countByTerm(term);

		List<Contact> contacts = contactDAO.findByTerm(term, startIndex, contactsPageSize);
		for (Contact contact : contacts) {
			if (contact.getCompany() != null) {
				contact.getCompany().getName().toString(); // force JPA to
				// fetch company
			}
		}
		return new Object[] { contactsTotalSize, contacts };
	}

	protected Object[] searchEmployees(String term, Integer startIndex, Integer employeesPageSize) {
		UserHierarchyDAO contactDAO = SpringUtil.getApplicationContext().getBean(UserHierarchyDAO.class);

		// set up paging by counting records first
		Integer employeesTotalSize = contactDAO.countEmployeesByTerm(term).intValue();

		List<UserHierarchy> employees = contactDAO.findEmployeesByTerm(term, startIndex, employeesPageSize);
		return new Object[] { employeesTotalSize, employees };
	}

	protected Object[] searchCompanies(String term, Integer startIndex, Integer companiesPageSize) {
		CompanyDAO companyDAO = SpringUtil.getApplicationContext().getBean(CompanyDAO.class);

		// set up paging by counting records first
		Integer companiesTotalSize = companyDAO.countByTerm(term).intValue();

		List<Company> companies = companyDAO.findByTerm(term, startIndex, companiesPageSize);
		return new Object[] { companiesTotalSize, companies };
	}

	protected Object[] searchProtocolCorrespondents(String term, CorrespondentDirection[] directions,
			CorrespondentType type, Integer startIndex, Integer pageSize) {
		ProtocolCorrespondentDAO protocolCorrespondentDAO = SpringUtil.getApplicationContext()
				.getBean(ProtocolCorrespondentDAO.class);

		// set up paging by counting records first
		Integer totalSize = protocolCorrespondentDAO.countByTermAndDirectionsAndType(term, directions, type);

		List<ProtocolCorrespondent> results = protocolCorrespondentDAO.findByTermAndDirectionsAndType(term, directions,
				type, startIndex, pageSize);
		return new Object[] { totalSize, results };
	}

	protected Object[] searchEmployees(String term, Department department, Integer startIndex,
			Integer employeesPageSize) {
		UserHierarchyDAO contactDAO = SpringUtil.getApplicationContext().getBean(UserHierarchyDAO.class);

		// set up paging by counting records first
		Integer employeesTotalSize = contactDAO.countEmployeesByTermAndDepartment(term, department).intValue();

		List<UserHierarchy> employees = contactDAO.findEmployeesByTermAndDepartment(term, department, startIndex,
				employeesPageSize);
		return new Object[] { employeesTotalSize, employees };
	}

	protected Object[] searchUnitDepartments(String term, Department parentDepartment, Integer startIndex,
			Integer pageSize) {
		DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);

		// set up paging by counting records first
		Integer departmentsTotalSize = departmentDAO
				.countUnitDepartmentsByTermAndParentDepartment(term, parentDepartment).intValue();

		List<Department> departments = departmentDAO.findUnitDepartmentsByTermAndParentDepartment(term,
				parentDepartment, startIndex, pageSize);
		return new Object[] { departmentsTotalSize, departments };
	}

	public Boolean getIsPendingFunctionalityEnabled() {
		return (Boolean) Executions.getCurrent().getDesktop().getWebApp()
				.getAttribute(IConstants.PARAM_ENABLE_PENDING_FUNCTIONALITY);
	}

	public Boolean getIsDossierFunctionalityEnabled() {
		return (Boolean) Executions.getCurrent().getDesktop().getWebApp()
				.getAttribute(IConstants.PARAM_ENABLE_DOSSIER_FUNCTIONALITY);
	}

	public Boolean getIsDocumentUploadFunctionalityEnabled() {
		return (Boolean) Executions.getCurrent().getDesktop().getWebApp()
				.getAttribute(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY);
	}

	public Integer getContentSearchResultsLimit() {
		return (Integer) Executions.getCurrent().getDesktop().getWebApp()
				.getAttribute(IConstants.PARAM_CONTENT_SEARCH_RESULTS_LIMIT);
	}

	@Command
	public void handleKeystroke(@ContextParam(ContextType.TRIGGER_EVENT) KeyEvent event) {
		if (event.getReference() instanceof Datebox) {
			Datebox datebox = (Datebox) event.getReference();
			Calendar instance = Calendar.getInstance();
			DateUtils.truncate(instance, Calendar.DATE);
			datebox.setValue(instance.getTime());
		}
	}

}
