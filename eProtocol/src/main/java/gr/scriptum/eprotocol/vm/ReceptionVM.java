/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import gr.scriptum.dao.DepartmentTypeDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReceptionVM extends BaseVM {

	private static Log log = LogFactory.getLog(ReceptionVM.class);

	public static final String PAGE = "reception.zul";

	public static final String PARAM_PROTOCOL = "PROTOCOL";

	public static final String PARAM_RECIPIENTS = "RECIPIENTS";

	public static final String PARAM_ASSIGNEES = "ASSIGNEES";

	@WireVariable
	private ProtocolDAO protocolDAO;

	@WireVariable
	private ProtocolService protocolService;

	/* data binding fields */
	private List<ProtocolCorrespondent> recipients;

	private Integer recipientsPageSize = PAGE_SIZE_DEFAULT;

	private Protocol protocol;

	private CorrespondentType[] assigneeTypes = { EMPLOYEE, DEPARTMENT };

	private CorrespondentType assigneeType = EMPLOYEE;

	private List<Department> departments;

	private Department department;

	private Integer departmentsTotalSize;

	private Integer departmentsPageSize = PAGE_SIZE_DEFAULT;

	private Integer departmentsActivePage;

	private List<UserHierarchy> employees;

	private UserHierarchy employee;

	private Integer employeesTotalSize;

	private Integer employeesPageSize = PAGE_SIZE_DEFAULT;

	private Integer employeesActivePage;

	private List<ProtocolCorrespondent> assignees;

	private ProtocolCorrespondent assignee;

	private Date currentDate;
	
	private Date currentAttachedDate;

	/* non VM-accessible fields */
	private String term;

	private Callback callback = null;
	
	private boolean dirty = false;

	private ProtocolCorrespondent getAssignee(UserHierarchy userHierarchy) {
		ProtocolCorrespondent assignee = new ProtocolCorrespondent();
		assignee.setDirection(ASSIGNEE);
		assignee.setType(EMPLOYEE);
		assignee.setContact(userHierarchy.getContact());
		assignee.setDepartment(userHierarchy.getDepartment());
		assignee.setDescription(userHierarchy.getContact().getFullName());
		assignee.setCode(userHierarchy.getContact().getCode());
		return assignee;
	}

	private ProtocolCorrespondent getAssignee(Department department) {
		ProtocolCorrespondent assignee = new ProtocolCorrespondent();
		assignee.setDirection(ASSIGNEE);
		assignee.setType(DEPARTMENT);
		assignee.setDepartment(department);
		assignee.setDescription(department.getName() + " (" + department.getCode() + ")");
		assignee.setCode(department.getCode());
		return assignee;
	}

	private boolean assigneeFoundInList(List<ProtocolCorrespondent> assignees, ProtocolCorrespondent assignee) {
		// ACTIVE_MEMBER, INACTIVE_MEMBER, EMPLOYEE, DEPARTMENT, COMPANY,
		// CONTACT, OTHER
		switch (assignee.getType()) {
		case EMPLOYEE:
			log.debug("employee");
			return assignees.stream()
					.anyMatch(r -> r.getType().equals(EMPLOYEE) && r.getContact().equals(assignee.getContact())
							&& r.getDepartment().equals(assignee.getDepartment()));
		case DEPARTMENT:
			log.debug("department");
			return assignees.stream().anyMatch(
					r -> r.getType().equals(DEPARTMENT) && r.getDepartment().equals(assignee.getDepartment()));
		default:
			return false;
		}
	}

	@Init
	public void init(@ContextParam(ContextType.EXECUTION) Execution execution) {
		// setup callback, if any
		// Execution execution = Executions.getCurrent();
		Map<?, ?> arg = execution.getArg();
		callback = (Callback) arg.get(IConstants.PARAM_CALLBACK);

		protocol = (Protocol) arg.get(PARAM_PROTOCOL);
		recipients = (List<ProtocolCorrespondent>) arg.get(PARAM_RECIPIENTS);
		assignees = arg.containsKey(PARAM_ASSIGNEES) ? (ArrayList<ProtocolCorrespondent>) arg.get(PARAM_ASSIGNEES)
				: new ArrayList<ProtocolCorrespondent>();
	}

	@Command
	// @NotifyChange("")
	public void clearAssignee() {
		// TODO
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void searchDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			departments = new ArrayList<Department>();
			return;
		}
		departmentsActivePage = 0;
		Object[] results = searchUnitDepartments(term, getUserInSessionDepartment(), 0, departmentsPageSize);
		departmentsTotalSize = (Integer) results[0];
		departments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void listDepartments() {
		if (departments != null && !departments.isEmpty()) {
			return;
		}
		term = "";
		departmentsActivePage = 0;
		Object[] results = searchUnitDepartments(term, getUserInSessionDepartment(), 0, departmentsPageSize);
		departmentsTotalSize = (Integer) results[0];
		departments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void clearDepartments() {
		departments = new ArrayList<Department>();
		departmentsTotalSize = 0;
		departmentsActivePage = 0;
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void pageDepartments() {
		int startIndex = departmentsActivePage * departmentsPageSize;
		Object[] results = searchUnitDepartments(term, getUserInSessionDepartment(), startIndex, departmentsPageSize);
		departmentsTotalSize = (Integer) results[0];
		departments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "assignees", "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void selectDepartment() {
		ProtocolCorrespondent departmentAssignee = getAssignee(department);
		if (!assigneeFoundInList(assignees, departmentAssignee)) {
			assignees.add(departmentAssignee);
		}
		departments = new ArrayList<Department>();
		department = null;
		departmentsTotalSize = 0;
		departmentsActivePage = 0;
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void searchEmployeesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			employees = new ArrayList<UserHierarchy>();
			return;
		}
		employeesActivePage = 0;
		Object[] results = searchEmployees(term, getUserInSessionDepartment(), 0, employeesPageSize);
		employeesTotalSize = (Integer) results[0];
		employees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void listEmployees() {
		if (employees != null && !employees.isEmpty()) {
			return;
		}
		term = "";
		employeesActivePage = 0;
		Object[] results = searchEmployees(term, getUserInSessionDepartment(), 0, employeesPageSize);
		employeesTotalSize = (Integer) results[0];
		employees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void clearEmployees() {
		employees = new ArrayList<UserHierarchy>();
		employeesTotalSize = 0;
		employeesActivePage = 0;
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void pageEmployees() {
		int startIndex = employeesActivePage * employeesPageSize;
		Object[] results = searchEmployees(term, getUserInSessionDepartment(), startIndex, employeesPageSize);
		employeesTotalSize = (Integer) results[0];
		employees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange("*")
	public void selectEmployee() {

		ProtocolCorrespondent employeeAssignee = getAssignee(employee);
		if (!assigneeFoundInList(assignees, employeeAssignee)) {
			assignees.add(employeeAssignee);
			dirty = true;
		}
		employees = new ArrayList<UserHierarchy>();
		employee = null;
		employeesTotalSize = 0;
		employeesActivePage = 0;
	}

	@Command
	@NotifyChange("*")
	public void removeAssignee() {
		assignees.remove(assignee);
		assignee = null;
		dirty = true;
	}

	@Command
	@NotifyChange("*")
	public void setRecipientsDate() {
		currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
		for (ProtocolCorrespondent recipient : recipients) {
			if (recipient.getDeliveryDate() == null) {
				recipient.setDeliveryDate(currentDate);
				dirty = true;
			}
		}
	}

	@Command
	@NotifyChange("*")
	public void setRecipientsAttachedDate() {
		currentAttachedDate = DateUtils.truncate(new Date(), Calendar.DATE);
		for (ProtocolCorrespondent recipient : recipients) {
			if (recipient.getProtocol().getAttachedNumber() != null && recipient.getAttachedDeliveryDate() == null) {
				recipient.setAttachedDeliveryDate(currentAttachedDate);
				dirty = true;
			}
		}
	}

	@Command
	public void cancelReception() {
		if (callback != null) {
			BindUtils.postGlobalCommand(null, null, callback.getEvent(), null);
		}
	}

	@Command
	public void confirmReception(@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (recipients != null) {
				// protocol reception
				protocolService.receiveProtocols(recipients, assignees, getUserInSession());
			} else {
				// assignees edit
				Object[] results = searchUnitDepartments("", getUserInSessionDepartment(), null, null);
				List<Department> departments = (List<Department>) results[1];
				departments.add(getUserInSessionDepartment());
				protocolService.replaceDepartmentAssignees(protocol, departments, assignees, getUserInSession());
			}

			Messagebox.show(Labels.getLabel("success"), Labels.getLabel("success.title"), Messagebox.OK,
					Messagebox.INFORMATION);

			if (callback != null) {
				// notify caller (if any)
				BindUtils.postGlobalCommand(null, null, callback.getEvent(), null);
			}

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("error"), Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		} finally {
			view.detach();
		}

	}

	public boolean isReceptionEnabled() {
		return recipients != null && recipients.stream().anyMatch(r -> r.getDeliveryDate() == null);
	}

	public boolean isAttachedReceptionEnabled() {
		if(recipients==null) {
			return false;
		}
		
		if(recipients.size()>1 && currentDate ==null) {
			return false;
		}
		
		return recipients.stream()
				.anyMatch(r -> (r.getProtocol().getAttachedNumber() != null && r.getAttachedDeliveryDate() == null));
	}

	public boolean isEditAssigneesEnabled() {
		if(recipients==null) {
			return true;
		}
//		vm.recipients ne null and empty vm.currentDate
		return recipients.stream().allMatch(r -> r.getDeliveryDate()!=null);
		
//		return false;
	}
	
	public boolean isConfirmReceptionEnabled() {
		if(recipients==null) {
			return true;
		}
		return dirty;
	}
	
	public List<ProtocolCorrespondent> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<ProtocolCorrespondent> recipients) {
		this.recipients = recipients;
	}

	public Integer getRecipientsPageSize() {
		return recipientsPageSize;
	}

	public void setRecipientsPageSize(Integer recipientsPageSize) {
		this.recipientsPageSize = recipientsPageSize;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public CorrespondentType[] getAssigneeTypes() {
		return assigneeTypes;
	}

	public void setAssigneeTypes(CorrespondentType[] assigneeTypes) {
		this.assigneeTypes = assigneeTypes;
	}

	public CorrespondentType getAssigneeType() {
		return assigneeType;
	}

	public void setAssigneeType(CorrespondentType assigneeType) {
		this.assigneeType = assigneeType;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getDepartmentsTotalSize() {
		return departmentsTotalSize;
	}

	public void setDepartmentsTotalSize(Integer departmentsTotalSize) {
		this.departmentsTotalSize = departmentsTotalSize;
	}

	public Integer getDepartmentsPageSize() {
		return departmentsPageSize;
	}

	public void setDepartmentsPageSize(Integer departmentsPageSize) {
		this.departmentsPageSize = departmentsPageSize;
	}

	public Integer getDepartmentsActivePage() {
		return departmentsActivePage;
	}

	public void setDepartmentsActivePage(Integer departmentsActivePage) {
		this.departmentsActivePage = departmentsActivePage;
	}

	public List<UserHierarchy> getEmployees() {
		return employees;
	}

	public void setEmployees(List<UserHierarchy> employees) {
		this.employees = employees;
	}

	public UserHierarchy getEmployee() {
		return employee;
	}

	public void setEmployee(UserHierarchy employee) {
		this.employee = employee;
	}

	public Integer getEmployeesTotalSize() {
		return employeesTotalSize;
	}

	public void setEmployeesTotalSize(Integer employeesTotalSize) {
		this.employeesTotalSize = employeesTotalSize;
	}

	public Integer getEmployeesPageSize() {
		return employeesPageSize;
	}

	public void setEmployeesPageSize(Integer employeesPageSize) {
		this.employeesPageSize = employeesPageSize;
	}

	public Integer getEmployeesActivePage() {
		return employeesActivePage;
	}

	public void setEmployeesActivePage(Integer employeesActivePage) {
		this.employeesActivePage = employeesActivePage;
	}

	public List<ProtocolCorrespondent> getAssignees() {
		return assignees;
	}

	public void setAssignees(List<ProtocolCorrespondent> assignees) {
		this.assignees = assignees;
	}

	public ProtocolCorrespondent getAssignee() {
		return assignee;
	}

	public void setAssignee(ProtocolCorrespondent assignee) {
		this.assignee = assignee;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getCurrentAttachedDate() {
		return currentAttachedDate;
	}

	public void setCurrentAttachedDate(Date currentAttachedDate) {
		this.currentAttachedDate = currentAttachedDate;
	}

}
