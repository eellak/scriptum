/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.ws.WebServiceContext;

import org.springframework.web.context.ContextLoader;

import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.SubjectDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolRelation;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.Subject;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Permission;
import gr.scriptum.eprotocol.wserver.ProtocolWebServiceFaultBean.ErrorCode;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class OutgoingProtocolMessageParser extends ProtocolMessageParser<OutgoingProtocolMessage> {

	public ProtocolReceipt receiveOutgoingProtocol(OutgoingProtocolMessage message, WebServiceContext wsContext,
			Validator validator) throws ProtocolWebServiceFault {

		Users user = authenticate(wsContext);
		if (user == null) {
			throw new ProtocolWebServiceFault(ErrorCode.AUTHENTICATION_ERROR, "Invalid credentials");
		}

		// collect user permissions
		Set<gr.scriptum.domain.Permission> userPermissions = getPermissions(user);

		// validate payload constraints
		Set<ConstraintViolation<OutgoingProtocolMessage>> constraintViolations = validator.validate(message);
		if (!constraintViolations.isEmpty()) {
			ProtocolWebServiceFaultBean faultBean = getFaultBean(constraintViolations);
			throw new ProtocolWebServiceFault(faultBean);
		}

		// validate subject (only one of code/subject must be present)
		if (!(message.getSubjectCode() != null ^ message.getSubject() != null)) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
					"subject,subjectCode:only one must be present");
		}

		// check if user can create an outgoing protocol
		boolean userCanWriteOutgoing = userPermissions.stream()
				.anyMatch(p -> p.getName().equals(Permission.WRITE_OUTGOING.toString()));
		if (!userCanWriteOutgoing) {
			throw new ProtocolWebServiceFault(ErrorCode.PERMISSIONS_ERROR, "Invalid permissions");
		}

		// TODO: validate recipients
		List<Recipient> messageRecipients = message.getRecipients();
		for (Recipient messageRecipient : messageRecipients) {
			CorrespondentType recipientType = messageRecipient.getType();
			switch (recipientType) {
			case EMPLOYEE:
			case DEPARTMENT:
			case COMPANY:
			case CONTACT:
				if (messageRecipient.getCode() == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "recipient.code: invalid: null");
				}
				break;
			case ACTIVE_MEMBER:
			case INACTIVE_MEMBER:
				if (messageRecipient.getCode() == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "recipient.code: invalid: null");
				}
				break;
			case OTHER:
				if (messageRecipient.getDescription() == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"recipient.description: invalid: null");
				}
				break;
			default:
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "recipient: invalid type");
			}
		}

		// validate transactors
		// nothing to do

		// fetch protocol book
		ProtocolBook protocolBook = getProtocolBook(message.getBook());
		if (protocolBook == null) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "book: invalid");
		}

		// check if user has proper permissions on book
		gr.scriptum.domain.Permission writeOutgoingPermission = new gr.scriptum.domain.Permission();
		writeOutgoingPermission.setName(Permission.WRITE_OUTGOING.toString());
		PermissionDAO permissionDAO = ContextLoader.getCurrentWebApplicationContext()
		.getBean(PermissionDAO.class);
		writeOutgoingPermission = permissionDAO.findByExample(writeOutgoingPermission).get(0);
		List<UserAssignment> bookAssignments = getBookAssignments(user, protocolBook, writeOutgoingPermission);
		if (bookAssignments.isEmpty()) {
			throw new ProtocolWebServiceFault(ErrorCode.PERMISSIONS_ERROR, "book: invalid permissions");
		}

		// fetch user department (trust client side that user belongs to
		// supplied department)
		Department department = getDepartment(message.getDepartment());
		if (department == null) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "department: invalid");
		}

		// initialize protocol (required for recipient parsing)
		Protocol protocol = new Protocol(Direction.OUTGOING);
		Calendar calendar = Calendar.getInstance();
		protocol.setProtocolYear(calendar.get(Calendar.YEAR));
		protocol.setProtocolDate(calendar.getTime());

		// parse author (if available)
		UserHierarchy author = null;
		if (message.getAuthor() != null) {
			UserHierarchyDAO userHierarchyDAO = ContextLoader.getCurrentWebApplicationContext()
					.getBean(UserHierarchyDAO.class);
			List<UserHierarchy> employees = userHierarchyDAO
					.findEmployeesByCodeAndDepartment(message.getAuthor().getCode(), department, null, null);
			if (employees.size() != 1) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "author.code: invalid");
			}
			author = employees.get(0);
		}

		// parse subject
		Subject subject = null;
		if (message.getSubjectCode() != null) {
			SubjectDAO subjectDAO = ContextLoader.getCurrentWebApplicationContext().getBean(SubjectDAO.class);
			List<Subject> subjects = subjectDAO.findByCodeAndProtocolBookAndApplicableTo(message.getSubjectCode(),
					protocolBook, ApplicableType.OUTGOING, null, null);
			if (subjects.size() != 1) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "subjectCode: invalid");
			}
			subject = subjects.get(0);
		}

		// fetch document type
		DocumentType documentType = getDocumentType(message);
		if (documentType == null) {
			// also check subject code (if available)
			if (subject == null) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "documentType: invalid");
			} else {
				documentType = subject.getDocumentType();
			}
		}
		if (!documentType.getApplicableTo().equals(ApplicableType.OUTGOING)) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "documentType: invalid");
		}

		// parse recipients
		List<ProtocolCorrespondent> recipients = new ArrayList<ProtocolCorrespondent>();

		ProtocolService protocolService = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ProtocolService.class);

		for (Recipient messageRecipient : messageRecipients) {
			ProtocolCorrespondent recipient = null;

			DistributionMethod distributionMethod = getDistributionMethod(messageRecipient.getDistributionMethod());
			if (distributionMethod == null) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
						"recipient.distributionMethod: invalid :" + messageRecipient.getDistributionMethod());
			}

			CorrespondentType recipientType = messageRecipient.getType();
			switch (recipientType) {
			case EMPLOYEE:
				UserHierarchy employee = (UserHierarchy) getCorrespondentEntityByTypeAndCode(recipientType,
						messageRecipient.getCode());
				if (employee == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"recipient.code: invalid: " + messageRecipient.getCode());
				}
				recipient = protocolService.getRecipient(employee, protocol, department, distributionMethod,
						messageRecipient.getAction());
				break;
			case DEPARTMENT:
				Department recipientDepartment = (Department) getCorrespondentEntityByTypeAndCode(recipientType,
						messageRecipient.getCode());
				if (recipientDepartment == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"recipient.code: invalid: " + messageRecipient.getCode());
				}
				recipient = protocolService.getRecipient(recipientDepartment, protocol, department, distributionMethod,
						messageRecipient.getAction());
				break;
			case COMPANY:
				Company recipientCompany = (Company) getCorrespondentEntityByTypeAndCode(recipientType,
						messageRecipient.getCode());
				if (recipientCompany == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"recipient.code: invalid: " + messageRecipient.getCode());
				}
				recipient = protocolService.getRecipient(recipientCompany, protocol, department, distributionMethod,
						messageRecipient.getAction());
				break;
			case CONTACT:
				Contact recipientContact = (Contact) getCorrespondentEntityByTypeAndCode(recipientType,
						messageRecipient.getCode());
				if (recipientContact == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"recipient.code: invalid: " + messageRecipient.getCode());
				}
				recipient = protocolService.getRecipient(recipientContact, protocol, department, distributionMethod,
						messageRecipient.getAction());
				break;
			case ACTIVE_MEMBER:
				recipient = protocolService.getRecipientActiveMember(messageRecipient.getCode(),
						messageRecipient.getDescription(), protocol, department, distributionMethod,
						messageRecipient.getAction());
				break;
			case INACTIVE_MEMBER:
				recipient = protocolService.getRecipientInactiveMember(messageRecipient.getCode(),
						messageRecipient.getDescription(), protocol, department, distributionMethod,
						messageRecipient.getAction());
				break;
			case OTHER:
				recipient = protocolService.getRecipient(messageRecipient.getDescription(), protocol, department,
						distributionMethod, messageRecipient.getAction());
				break;
			default:
				break;
			}
			if (protocolService.recipientFoundInList(recipients, recipient)) {
				throw new ProtocolWebServiceFault(ErrorCode.DUPLICATE_VALUE,
						"recipient: duplicate :" + messageRecipient.getType() + "/" + messageRecipient.getCode());
			}
			recipients.add(recipient);
		}

		// parse transactors
		if (!protocolService.recipientsContainAnyInternalTypes(recipients)) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,"transactors: not allowed");
		}
		Set<ProtocolCorrespondent> transactors = new HashSet<>();
		List<Transactor> messageTransactors = message.getTransactors();
		if (messageTransactors != null) {
			for (Transactor messageTransactor : messageTransactors) {
				ProtocolCorrespondent transactor = getTransactor(messageTransactor);
				if (transactorFoundInList(transactor, transactors)) {
					throw new ProtocolWebServiceFault(ErrorCode.DUPLICATE_VALUE,"transactors.transactor: duplicate value:" + messageTransactor.getType() + "/"
							+ messageTransactor.getCode() + "/" + messageTransactor.getDescription());
				}
				transactors.add(transactor);
			}
		}

		// add an extra transactor for each active/inactive member recipient
		for (ProtocolCorrespondent recipient : recipients) {
			if (recipient.getType().equals(CorrespondentType.ACTIVE_MEMBER)
					|| recipient.getType().equals(CorrespondentType.INACTIVE_MEMBER)) {
				ProtocolCorrespondent transactor = new ProtocolCorrespondent();
				transactor.setDirection(TRANSACTOR);
				transactor.setType(recipient.getType());
				transactor.setCode(recipient.getCode());
				transactor.setDescription(recipient.getDescription());
				transactor.setVatNumber(recipient.getVatNumber());
				transactor.setSsn(recipient.getSsn());
				if (transactorFoundInList(transactor, transactors)) {
					throw new ProtocolWebServiceFault(ErrorCode.DUPLICATE_VALUE,"transactors.transactor: duplicate value:" + transactor.getType() + "/"
							+ transactor.getCode() + "/" + transactor.getDescription());
				}
				transactors.add(transactor);
			}
		}

		// parse relative protocols
		Set<ProtocolRelation> protocolRelations = getProtocolRelations(message, protocolBook);

		// Create the protocol
		protocol.setProtocolCorrespondents(new HashSet<ProtocolCorrespondent>());
		protocol.setExternalSystemId(message.getExternalSystemId());
		protocol.setExternalUserId(message.getExternalUserId());
		protocol.setProtocolBook(protocolBook);
		if (author != null) {
			protocol.setAuthor(author);
		}
		protocol.setTransactors(transactors);
		protocol.setIncomingProtocolNumber(message.getExternalProtocolNumber());
		protocol.setIncomingDate(message.getDocumentDate());
		if (subject != null) {
			protocol.setSubjectCode(subject.getCode());
			protocol.setSubject(subject.getDescription());
		} else {
			protocol.setSubject(message.getSubject());
		}
		protocol.setComments(message.getComments());
		protocol.setAttachedNumber(message.getAttachedNumber());
		protocol.setAttachedDescription(message.getAttachedDescription());
		protocol.setDocumentType(documentType);
		protocol.setRecipients(new HashSet<>(recipients));

		protocolRelations.forEach((pr) -> pr.setSourceProtocol(protocol));
		protocol.setProtocolRelationsAsSource(protocolRelations);

		try {
			protocolService.saveOutgoingProtocol(protocol, user, department, getClientIp(wsContext),
					new ArrayList<ProtocolDocument>(), null, true);
		} catch (OpenKMException e) {
			throw new ProtocolWebServiceFault(
					new ProtocolWebServiceFaultBean(ErrorCode.INSERTION_ERROR, "Error contacting OpenKM"), e);
		}

		ProtocolReceipt protocolReceipt = new ProtocolReceipt();
		protocolReceipt.setProtocolId(protocol.getId());
		protocolReceipt.setProtocolNumber(protocol.getProtocolNumber());
		protocolReceipt.setProtocolDate(protocol.getProtocolDate());
		protocolReceipt.setReceivedDate(new Date());
		protocolReceipt.setDirection(protocol.getDirection());

		return protocolReceipt;
	}

}
