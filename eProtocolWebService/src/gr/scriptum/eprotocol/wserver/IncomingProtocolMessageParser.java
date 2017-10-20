/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;

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

import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.SubjectDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ProtocolRelation;
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
public class IncomingProtocolMessageParser extends ProtocolMessageParser<IncomingProtocolMessage> {

	public ProtocolReceipt receiveIncomingProtocol(IncomingProtocolMessage message, WebServiceContext wsContext,
			Validator validator) throws ProtocolWebServiceFault {

		Users user = authenticate(wsContext);
		if (user == null) {
			throw new ProtocolWebServiceFault(ErrorCode.AUTHENTICATION_ERROR, "Invalid credentials");
		}

		ProtocolService protocolService = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ProtocolService.class);

		// collect user permissions
		Set<gr.scriptum.domain.Permission> userPermissions = getPermissions(user);

		// validate payload constraints
		Set<ConstraintViolation<IncomingProtocolMessage>> constraintViolations = validator.validate(message);
		if (!constraintViolations.isEmpty()) {
			ProtocolWebServiceFaultBean faultBean = getFaultBean(constraintViolations);
			throw new ProtocolWebServiceFault(faultBean);
		}

		// validate subject (only one of code/subject must be present)
		if (!(message.getSubjectCode() != null ^ message.getSubject() != null)) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
					"subject,subjectCode:only one must be present");
		}

		// check if user can create an incoming protocol
		boolean userCanWriteIncoming = userPermissions.stream()
				.anyMatch(p -> p.getName().equals(Permission.WRITE_INCOMING.toString()));
		if (!userCanWriteIncoming) {
			throw new ProtocolWebServiceFault(ErrorCode.PERMISSIONS_ERROR, "Invalid permissions");
		}

		// validate internal recipients
		boolean userCanAssignInternalRecipients = userPermissions.stream()
				.anyMatch(p -> p.getName().equals(Permission.ASSIGN_INTERNAL_RECIPIENTS.toString()));

		// if user has internal recipients permission, then at least one
		// recipient must be present
		if (userCanAssignInternalRecipients) {
			if (message.getInternalRecipients() == null || message.getInternalRecipients().isEmpty()) {
				throw new ProtocolWebServiceFault(ErrorCode.PERMISSIONS_ERROR, "internalRecipients: user must assign");
			}
		} else {
			// use doesn't have permission, internal recipients must be empty
			if (message.getInternalRecipients() != null && !message.getInternalRecipients().isEmpty()) {
				// check if user can assign internal recipients
				throw new ProtocolWebServiceFault(ErrorCode.PERMISSIONS_ERROR,
						"internalRecipients: user cannot assign");
			}
		}

		// validate transactors
		if ((message.getSender().getType().equals(ACTIVE_MEMBER)
				|| message.getSender().getType().equals(INACTIVE_MEMBER)) && message.getTransactors() != null
				&& !message.getTransactors().isEmpty()) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
					"transactors: must be empty for sender.type:" + message.getSender().getType());
		}

		// fetch protocol book
		ProtocolBook protocolBook = getProtocolBook(message.getBook());
		if (protocolBook == null) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "book:invalid");
		}

		// fetch distribution method
		DistributionMethod distributionMethod = getDistributionMethod(message);
		if (distributionMethod == null) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "distributionMethod:invalid");
		}

		// check if user has proper permissions on book
		gr.scriptum.domain.Permission writeIncomingPermission = new gr.scriptum.domain.Permission();
		writeIncomingPermission.setName(Permission.WRITE_INCOMING.toString());
		PermissionDAO permissionDAO = ContextLoader.getCurrentWebApplicationContext().getBean(PermissionDAO.class);
		writeIncomingPermission = permissionDAO.findByExample(writeIncomingPermission).get(0);
		List<UserAssignment> bookAssignments = getBookAssignments(user, protocolBook, writeIncomingPermission);
		if (bookAssignments.isEmpty()) {
			throw new ProtocolWebServiceFault(ErrorCode.PERMISSIONS_ERROR, "book:invalid permissions");
		}

		// fetch user department (trust client side that user belongs to
		// supplied department)
		Department department = getDepartment(message.getDepartment());
		if (department == null) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "department: invalid");
		}

		// initialize protocol (required for internal recipient parsing)
		Protocol protocol = new Protocol(Direction.INCOMING);
		Calendar calendar = Calendar.getInstance();
		protocol.setProtocolYear(calendar.get(Calendar.YEAR));
		protocol.setProtocolDate(calendar.getTime());

		// parse sender
		Object sender = null;
		CorrespondentType senderType = message.getSender().getType();
		switch (senderType) {
		case EMPLOYEE:
		case DEPARTMENT:
		case COMPANY:
		case CONTACT:
			if (message.getSender().getCode() == null) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "sender: invalid code");
			}
			sender = getCorrespondentEntityByTypeAndCode(message.getSender().getType(), message.getSender().getCode());
			if (sender == null) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "sender: invalid code");
			}
			break;
		case ACTIVE_MEMBER:
		case INACTIVE_MEMBER:
			if (message.getSender().getCode() == null) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "sender.code: invalid :null");
			}
			ProtocolCorrespondent memberSender = new ProtocolCorrespondent();
			memberSender.setType(senderType);
			memberSender.setCode(message.getSender().getCode());
			memberSender.setDescription(message.getSender().getDescription());
			memberSender.setVatNumber(message.getSender().getVatNumber());
			memberSender.setSsn(message.getSender().getSsn());
			sender = memberSender;
			break;
		case OTHER:
			if (message.getSender().getDescription() == null) {
				throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "sender: invalid description");
			}
			sender = message.getSender().getDescription();
			break;
		default:
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "sender: invalid type");
		}

		// parse subject
		Subject subject = null;
		if (message.getSubjectCode() != null) {
			SubjectDAO subjectDAO = ContextLoader.getCurrentWebApplicationContext().getBean(SubjectDAO.class);
			List<Subject> subjects = subjectDAO.findByCodeAndProtocolBookAndApplicableTo(message.getSubjectCode(),
					protocolBook, ApplicableType.INCOMING, null, null);
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
		if (!documentType.getApplicableTo().equals(ApplicableType.INCOMING)) {
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "documentType: invalid");
		}

		// parse internal recipients
		Set<ProtocolCorrespondent> internalRecipients = new HashSet<ProtocolCorrespondent>();
		List<InternalRecipient> messageInternalRecipients = message.getInternalRecipients();
		if (messageInternalRecipients != null) {

			for (InternalRecipient messageInternalRecipient : messageInternalRecipients) {
				Department internalRecipientDepartment = (Department) getCorrespondentEntityByTypeAndCode(DEPARTMENT,
						messageInternalRecipient.getCode());
				if (internalRecipientDepartment == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"internalRecipient.code: invalid :" + messageInternalRecipient.getCode());
				}
				DistributionMethod internalRecipientDistributionMethod = getDistributionMethod(
						messageInternalRecipient.getDistributionMethod());
				if (internalRecipientDistributionMethod == null) {
					throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR,
							"internalRecipient.distributionMethod: invalid :"
									+ messageInternalRecipient.getDistributionMethod());
				}

				ProtocolCorrespondent internalRecipient = protocolService.getInternalRecipient(
						internalRecipientDepartment, protocol, internalRecipientDistributionMethod);
				internalRecipient.setAction(messageInternalRecipient.getAction());
				internalRecipients.add(internalRecipient);
			}
		}

		// parse transactors
		Set<ProtocolCorrespondent> transactors = new HashSet<>();
		List<Transactor> messageTransactors = message.getTransactors();
		if (messageTransactors != null) {
			for (Transactor messageTransactor : messageTransactors) {
				ProtocolCorrespondent transactor = getTransactor(messageTransactor);
				if (transactorFoundInList(transactor, transactors)) {
					throw new ProtocolWebServiceFault(ErrorCode.DUPLICATE_VALUE,
							"transactors.transactor: duplicate value:" + messageTransactor.getType() + "/"
									+ messageTransactor.getCode() + "/" + messageTransactor.getDescription());
				}
				transactors.add(transactor);
			}
		}

		// parse relative protocols
		Set<ProtocolRelation> protocolRelations = getProtocolRelations(message, protocolBook);

		protocol.setProtocolCorrespondents(new HashSet<ProtocolCorrespondent>());
		protocol.setExternalSystemId(message.getExternalSystemId());
		protocol.setExternalUserId(message.getExternalUserId());
		protocol.setProtocolBook(protocolBook);
		protocol.setDistributionMethod(distributionMethod);
		protocol.setDistributionMethodDetails(message.getDistributionMethodDetails());
		switch (senderType) {
		case EMPLOYEE:
			protocol.setSender((UserHierarchy) sender);
			break;
		case DEPARTMENT:
			protocol.setSender((Department) sender);
			break;
		case COMPANY:
			protocol.setSender((Company) sender);
			break;
		case CONTACT:
			protocol.setSender((Contact) sender);
			break;
		case ACTIVE_MEMBER:
		case INACTIVE_MEMBER:
			protocol.setSender((ProtocolCorrespondent) sender);
			break;
		case OTHER:
			protocol.setSender((String) sender);
			break;
		default:
			throw new ProtocolWebServiceFault(ErrorCode.VALIDATION_ERROR, "sender:invalid type");
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
		protocol.setInternalRecipients(internalRecipients);

		protocolRelations.forEach((pr) -> pr.setSourceProtocol(protocol));
		protocol.setProtocolRelationsAsSource(protocolRelations);

		// check for duplicate submission
		List<Protocol> alreadySubmittedProtocols = protocolService.findAlreadySubmittedProtocols(protocol, department);
		if (alreadySubmittedProtocols.size() > 0) {
			throw new ProtocolWebServiceFault(ErrorCode.DUPLICATE_VALUE, "protocol: already submitted");
			// throw new Exception("protocol: already submitted");
		}

		try {
			protocolService.saveIncomingProtocol(protocol, user, department, getClientIp(wsContext),
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
