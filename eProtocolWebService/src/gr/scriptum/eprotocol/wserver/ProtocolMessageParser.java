/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static java.lang.Boolean.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.web.context.ContextLoader;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.UserAssignmentDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolRelation;
import gr.scriptum.domain.ProtocolRelation.RelationType;
import gr.scriptum.eprotocol.service.PermissionService;
import gr.scriptum.eprotocol.wserver.ProtocolWebServiceFaultBean.ErrorCode;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class ProtocolMessageParser<T> {

	private static Log log = LogFactory.getLog(ProtocolMessageParser.class);

	protected Users authenticate(WebServiceContext wsContext) {
		Map http_headers = (Map) wsContext.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS);
		List userList = (List) http_headers.get("username");
		List passList = (List) http_headers.get("password");

		String username = null;
		String password = null;

		if (userList != null) {
			// get username
			username = StringUtils.trimToNull(userList.get(0).toString());
		}

		if (passList != null) {
			// get password
			password = StringUtils.trimToNull(passList.get(0).toString());
		}

		if (username == null || password == null) {
			return null;
		}

		UsersDAO usersDAO = ContextLoader.getCurrentWebApplicationContext().getBean(UsersDAO.class);
		// login against local database
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setIsDisabled(FALSE);
		List<Users> users = usersDAO.findByExample(user, new String[] {});

		if (users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}

	protected DocumentType getDocumentType(BaseProtocolMessage message) {
		DocumentTypeDAO documentTypeDAO = ContextLoader.getCurrentWebApplicationContext()
				.getBean(DocumentTypeDAO.class);
		DocumentType documentType = documentTypeDAO.findById(message.getDocumentType(), false);
		return documentType;
	}

	protected Department getDepartment(String code) {
		DepartmentDAO departmentDAO = ContextLoader.getCurrentWebApplicationContext().getBean(DepartmentDAO.class);
		Department department = new Department();
		department.setCode(code);
		List<Department> departments = departmentDAO.findByExample(department);
		return departments.size() == 1 ? departments.get(0) : null;
	}

	protected List<UserAssignment> getBookAssignments(Users user, ProtocolBook protocolBook, Permission permission) {
		UserAssignmentDAO userAssignmentDAO = ContextLoader.getCurrentWebApplicationContext()
				.getBean(UserAssignmentDAO.class);
		List<UserAssignment> bookAssignments = userAssignmentDAO.findByUserAndEntityTypeAndEntityId(user,
				ProtocolBook.class, protocolBook.getId(), permission);
		return bookAssignments;
		// TODO: enable specific permission check
		// boolean userHasWriteIncomingPermissionOnBook =
		// bookAssignments.stream()
		// .anyMatch(ba ->
		// ba.getPermission().getName().equals(Permission.WRITE_INCOMING));
		// if(!userHasWriteIncomingPermissionOnBook) {
		// throw new Exception("invalid protocol book permissions");
		// }
	}

	protected ProtocolBook getProtocolBook(Integer bookId) {
		ProtocolBookDAO protocolBookDAO = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ProtocolBookDAO.class);
		ProtocolBook protocolBook = protocolBookDAO.findById(bookId, false);
		return protocolBook;
	}

	protected DistributionMethod getDistributionMethod(IncomingProtocolMessage message) {
		DistributionMethodDAO distributionMethodDAO = ContextLoader.getCurrentWebApplicationContext()
				.getBean(DistributionMethodDAO.class);
		DistributionMethod distributionMethod = new DistributionMethod();
		distributionMethod.setCode(message.getDistributionMethod());
		List<DistributionMethod> distributionMethods = distributionMethodDAO.findByExample(distributionMethod);
		return distributionMethods.size() == 1 ? distributionMethods.get(0) : null;
	}

	protected Set<gr.scriptum.domain.Permission> getPermissions(Users user) {
		PermissionService permissionService = ContextLoader.getCurrentWebApplicationContext()
				.getBean(PermissionService.class);
		Set<gr.scriptum.domain.Permission> userPermissions = permissionService.getUserPermissions(user);
		return userPermissions;
	}

	protected Object getCorrespondentEntityByTypeAndCode(CorrespondentType type, String code) {
		// EMPLOYEE, DEPARTMENT, COMPANY, CONTACT
		switch (type) {
		case EMPLOYEE:
			UserHierarchyDAO userHierarchyDAO = ContextLoader.getCurrentWebApplicationContext()
					.getBean(UserHierarchyDAO.class);
			List<UserHierarchy> employeesByCode = userHierarchyDAO.findEmployeesByCode(code, null, null);
			return employeesByCode.size() == 1 ? employeesByCode.get(0) : null;
		case DEPARTMENT:
			DepartmentDAO departmentDAO = ContextLoader.getCurrentWebApplicationContext().getBean(DepartmentDAO.class);
			Department department = new Department();
			department.setCode(code);
			List<Department> departments = departmentDAO.findByExample(department);
			return departments.size() == 1 ? departments.get(0) : null;
		case COMPANY:
			CompanyDAO companyDAO = ContextLoader.getCurrentWebApplicationContext().getBean(CompanyDAO.class);
			Company company = new Company();
			company.setCode(code);
			List<Company> companies = companyDAO.findByExample(company);
			return companies.size() == 1 ? companies.get(0) : null;
		case CONTACT:
			ContactDAO contactDAO = ContextLoader.getCurrentWebApplicationContext().getBean(ContactDAO.class);
			Contact contact = new Contact();
			contact.setCode(code);
			List<Contact> contacts = contactDAO.findByExample(contact);
			return contacts.size() == 1 ? contacts.get(0) : null;
		default:
			break;
		}
		return null;
	}

	protected boolean transactorFoundInList(ProtocolCorrespondent transactor, Set<ProtocolCorrespondent> transactors) {
		return transactors.stream().anyMatch(t -> t.getType().equals(transactor.getType())
				&& t.getCode().equals(transactor.getCode()) && t.getDescription().equals(transactor.getDescription()));
		// && t.getVatNumber().equals(transactor.getVatNumber()) &&
		// t.getSsn().equals(transactor.getSsn()));
	}

	protected final String getClientIp(WebServiceContext wsContext) {

		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		return req.getRemoteAddr();
	}

	protected final String getServerIp() {
		String serverIp = "localhost";
		try {
			serverIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error(e);
		}
		return serverIp;
	}

	protected DistributionMethod getDistributionMethod(String code) {
		DistributionMethodDAO distributionMethodDAO = ContextLoader.getCurrentWebApplicationContext()
				.getBean(DistributionMethodDAO.class);
		DistributionMethod example = new DistributionMethod();
		example.setCode(code);
		List<DistributionMethod> distributionMethods = distributionMethodDAO.findByExample(example);
		return distributionMethods.size() == 1 ? distributionMethods.get(0) : null;
	}

	protected ProtocolCorrespondent getTransactor(Transactor messageTransactor) {
		ProtocolCorrespondent transactor = new ProtocolCorrespondent();
		transactor.setDirection(TRANSACTOR);
		transactor.setType(CorrespondentType.valueOf(messageTransactor.getType().toString()));
		transactor.setCode(messageTransactor.getCode());
		transactor.setDescription(messageTransactor.getDescription());
		transactor.setVatNumber(messageTransactor.getVatNumber());
		transactor.setSsn(messageTransactor.getSsn());
		return transactor;
	}

	protected Set<ProtocolRelation> getProtocolRelations(BaseProtocolMessage message, ProtocolBook protocolBook) throws ProtocolWebServiceFault {
		// parse relative protocols
		Set<ProtocolRelation> protocolRelations = new HashSet<ProtocolRelation>();
		List<RelativeProtocol> relativeProtocols = message.getRelativeProtocols();
		if (relativeProtocols != null) {
			ProtocolDAO protocolDAO = ContextLoader.getCurrentWebApplicationContext().getBean(ProtocolDAO.class);
			List<ProtocolBook> protocolBooks = new ArrayList<ProtocolBook>();
			protocolBooks.add(protocolBook);
			List<Order> sortBy = new ArrayList<Order>();
			sortBy.add(Order.desc("protocolYear"));
			sortBy.add(Order.desc("protocolNumber"));

			for (RelativeProtocol relativeProtocol : relativeProtocols) {
				List<Protocol> results = protocolDAO.search(relativeProtocol.getDirection(), relativeProtocol.getYear(),
						relativeProtocol.getNumber(), null, null, null, null, null, null, null, null, null, null, null,
						null, null, false, false, protocolBooks, null, null, null, null, sortBy.toArray(new Order[0]));
				if (results.size() != 1) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, 
							"relativeProtocols.relativeProtocol: invalid value:" + relativeProtocol.getDirection() + "/"
									+ relativeProtocol.getNumber() + "/" + relativeProtocol.getYear());
				}
				Protocol protocol = results.get(0);
				boolean alreadyRelated = protocolRelations.stream().anyMatch(
						pr -> protocol.equals(pr.getSourceProtocol()) || protocol.equals(pr.getTargetProtocol()));
				if (alreadyRelated) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, 
							"relativeProtocols.relativeProtocol: duplicate value:" + relativeProtocol.getDirection()
							+ "/" + relativeProtocol.getNumber() + "/" + relativeProtocol.getYear());
				}
				ProtocolRelation protocolRelation = new ProtocolRelation();
				// protocolRelation.setSourceProtocol(protocol);
				protocolRelation.setTargetProtocol(protocol);
				protocolRelation.setRelationType(RelationType.RELATIVE);
				protocolRelations.add(protocolRelation);
			}
		}
		return protocolRelations;
	}

	protected ProtocolWebServiceFaultBean getFaultBean(Set<ConstraintViolation<T>> constraintViolations) {
		List<String> reasons = new ArrayList<String>();
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String path = constraintViolation.getPropertyPath().toString();
			String fieldMessage = constraintViolation.getMessage();
			String reason = path + ": " + fieldMessage;
			reasons.add(reason);
		}
		ProtocolWebServiceFaultBean faultBean = new ProtocolWebServiceFaultBean(ErrorCode.VALIDATION_ERROR,
				reasons);
		return faultBean;
	}
}
