/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import gr.scriptum.dao.CorrespondentGroupDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.eprotocol.service.CorrespondentGroupService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.EntitySelection;
import gr.scriptum.eprotocol.util.IConstants;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CorrespondentGroupVM extends GenericEntityVM<CorrespondentGroup, Long, CorrespondentGroupDAO> {

	public static final String PAGE = "correspondentGroup.zul";

	private static Log log = LogFactory.getLog(CorrespondentGroupVM.class);
	
	@WireVariable
	private CorrespondentGroupService correspondentGroupService;

	private CorrespondentDirection[] correspondentDirections = { INTERNAL_RECIPIENT, RECIPIENT };

	private CorrespondentType[] correspondentTypes;

	private CorrespondentType correspondentType = DEPARTMENT;

	private EntitySelection<Object> entitySelection;

	private ProtocolCorrespondent protocolCorrespondent;

	private Object[] searchEntities(String term, Integer startIndex, Integer pageSize) {
		Object[] results = null;
		switch (correspondentType) {
		case CONTACT:
			results = searchContacts(term, startIndex, pageSize);
			break;
		case EMPLOYEE:
			results = searchEmployees(term, startIndex, pageSize);
			break;
		case DEPARTMENT:
			results = searchProtocolDepartments(term, startIndex, pageSize);
			break;
		case COMPANY:
			results = searchCompanies(term, startIndex, pageSize);
			break;
		}
		return results;
	}

	private ProtocolCorrespondent getMember(Object entity) {
		ProtocolCorrespondent member = null;
		switch (correspondentType) {
		case CONTACT:
			member = getMember((Contact) entity);
			break;
		case EMPLOYEE:
			member = getMember((UserHierarchy) entity);
			break;
		case DEPARTMENT:
			member = getMember((Department) entity);
			break;
		case COMPANY:
			member = getMember((Company)entity);
			break;
		}
		return member;
	}

	private ProtocolCorrespondent getMember(UserHierarchy userHierarchy) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent();
		protocolCorrespondent.setCorrespondentGroup(entity);
		protocolCorrespondent.setType(EMPLOYEE);
		protocolCorrespondent.setContact(userHierarchy.getContact());
		protocolCorrespondent.setDepartment(userHierarchy.getDepartment());
		protocolCorrespondent.setDescription(userHierarchy.getContact().getFullName());
		protocolCorrespondent.setCode(userHierarchy.getContact().getCode());
		return protocolCorrespondent;
	}

	private ProtocolCorrespondent getMember(Contact contact) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent();
		protocolCorrespondent.setCorrespondentGroup(entity);
		protocolCorrespondent.setType(CONTACT);
		protocolCorrespondent.setContact(contact);
		protocolCorrespondent.setDescription(contact.getFullName());
		protocolCorrespondent.setVatNumber(contact.getVatNumber());
		protocolCorrespondent.setSsn(contact.getSsn());
		return protocolCorrespondent;
	}

	private ProtocolCorrespondent getMember(Department department) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent();
		protocolCorrespondent.setCorrespondentGroup(entity);
		protocolCorrespondent.setType(DEPARTMENT);
		protocolCorrespondent.setDepartment(department);
		protocolCorrespondent.setDescription(department.getName() + " (" + department.getCode() + ")");
		protocolCorrespondent.setCode(department.getCode());
		return protocolCorrespondent;
	}

	private ProtocolCorrespondent getMember(Company company) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent();
		protocolCorrespondent.setCorrespondentGroup(entity);
		protocolCorrespondent.setType(COMPANY);
		protocolCorrespondent.setCompany(company);
		protocolCorrespondent.setDescription(company.getName());
		return protocolCorrespondent;
	}

	private boolean memberFoundInList(Set<ProtocolCorrespondent> member, ProtocolCorrespondent recipient) {
		switch (recipient.getType()) {
		case EMPLOYEE:
			log.debug("employee");
			return member.stream()
					.anyMatch(r -> r.getType().equals(EMPLOYEE) && r.getContact().equals(recipient.getContact())
							&& r.getDepartment().equals(recipient.getDepartment()));
		case DEPARTMENT:
			log.debug("department");
			return member.stream().anyMatch(
					r -> r.getType().equals(DEPARTMENT) && r.getDepartment().equals(recipient.getDepartment()));
		case COMPANY:
			log.debug("company");
			return member.stream()
					.anyMatch(r -> r.getType().equals(COMPANY) && r.getCompany().equals(recipient.getCompany()));
		case CONTACT:
			log.debug("contact");
			return member.stream()
					.anyMatch(r -> r.getType().equals(CONTACT) && r.getContact().equals(recipient.getContact()));
		default:
			return false;
		}
	}

	private void initEntitySelection() {
		entitySelection = new EntitySelection<Object>();
		entitySelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	@Override
	protected void save() throws Exception {

		if (entity.getId() == null) { // new entity, create
			correspondentGroupService.create(entity, getUserInSession());
		} else {
			entity = correspondentGroupService.update(entity, getUserInSession());
		}
	}
 
	@Init(superclass = true)
	public void initVM() {
		if (entity.getId() == null) {
			entity.setProtocolCorrespondents(new HashSet<>());
			entity.setApplicableTo(INTERNAL_RECIPIENT);
		}
		initCorrespondentTypes();
		initEntitySelection();
	}

	@Override
	@Command
	@NotifyChange("*")
	public void addNewEntity() throws Exception {
		super.addNewEntity();
		entity.setProtocolCorrespondents(new HashSet<>());
		entity.setApplicableTo(INTERNAL_RECIPIENT);
		initCorrespondentTypes();
		initEntitySelection();
	}

	@Command
	@NotifyChange({ "entity", "correspondentTypes", "correspondentType", "term" })
	public void initCorrespondentTypes() {
		switch (entity.getApplicableTo()) {
		case INTERNAL_RECIPIENT:
			correspondentTypes = new CorrespondentType[] { DEPARTMENT };
			break;
		case RECIPIENT:
			correspondentTypes = new CorrespondentType[] { EMPLOYEE, DEPARTMENT, COMPANY, CONTACT };
			break;
		default:
			correspondentTypes = null;
			break;
		}
		correspondentType = DEPARTMENT;
		if (entity.getId() == null) {
			entity.setProtocolCorrespondents(new HashSet<>());
		}
	}

	@Command
	@NotifyChange("entitySelection")
	public void searchEntitiesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		entitySelection.setTerm(StringUtils.trimToNull(event.getValue()));
		entitySelection.setActivePage(0);
		Object[] results = searchEntities(entitySelection.getTerm(), 0, entitySelection.getPageSize());
		entitySelection.setTotalSize((Integer) results[0]);
		entitySelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("entitySelection")
	public void listEntities() {
		if (entitySelection.getEntites() != null && !entitySelection.getEntites().isEmpty()) {
			return;
		}
		entitySelection.setTerm("");
		entitySelection.setActivePage(0);
		Object[] results = searchEntities(entitySelection.getTerm(), 0, entitySelection.getPageSize());
		entitySelection.setTotalSize((Integer) results[0]);
		entitySelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("entitySelection")
	public void clearEntities() {
		entitySelection.setEntites(new ArrayList<Object>());
		entitySelection.setTotalSize(0);
		entitySelection.setActivePage(0);
	}

	@Command
	@NotifyChange("entitySelection")
	public void pageEntities() {
		int startIndex = entitySelection.getActivePage() * entitySelection.getPageSize();
		Object[] results = searchEntities(entitySelection.getTerm(), startIndex, entitySelection.getPageSize());
		entitySelection.setTotalSize((Integer) results[0]);
		entitySelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange({ "entity", "entitySelection" })
	public void selectEntity() {
		ProtocolCorrespondent recipient = getMember(entitySelection.getSelectedEntity());
		if (!memberFoundInList(entity.getProtocolCorrespondents(), recipient)) {
			entity.getProtocolCorrespondents().add(recipient);
		}
		initEntitySelection();
	}

	@Command
	public void addContact(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshEntities"));

		Window contactWin = (Window) Executions.createComponents(ContactVM.PAGE, view, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	@Command
	public void addCompany(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshMembers"));

		Window contactWin = (Window) Executions.createComponents(CompanyVM.PAGE, view, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	@GlobalCommand
	@NotifyChange({ "entity", "entitySelection" })
	public void refreshMembers(@BindingParam("entity") Object entity) {
		ProtocolCorrespondent member = getMember(entity);
		this.entity.getProtocolCorrespondents().add(member);
		initEntitySelection();
	}

	@Command
	@NotifyChange("entity")
	public void removeProtocolCorrespondent() {
		entity.getProtocolCorrespondents().remove(protocolCorrespondent);
	}

	public CorrespondentDirection[] getCorrespondentDirections() {
		return correspondentDirections;
	}

	public CorrespondentType[] getCorrespondentTypes() {
		return correspondentTypes;
	}

	public CorrespondentType getCorrespondentType() {
		return correspondentType;
	}

	public void setCorrespondentType(CorrespondentType correspondentType) {
		this.correspondentType = correspondentType;
	}

	public ProtocolCorrespondent getProtocolCorrespondent() {
		return protocolCorrespondent;
	}

	public void setProtocolCorrespondent(ProtocolCorrespondent protocolCorrespondent) {
		this.protocolCorrespondent = protocolCorrespondent;
	}

	public void setCorrespondentDirections(CorrespondentDirection[] correspondentDirections) {
		this.correspondentDirections = correspondentDirections;
	}

	public void setCorrespondentTypes(CorrespondentType[] correspondentTypes) {
		this.correspondentTypes = correspondentTypes;
	}

	public EntitySelection<Object> getEntitySelection() {
		return entitySelection;
	}

	public void setEntitySelection(EntitySelection<Object> entitySelection) {
		this.entitySelection = entitySelection;
	}

}
