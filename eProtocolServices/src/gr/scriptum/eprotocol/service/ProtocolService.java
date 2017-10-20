/**
 * 
 */
package gr.scriptum.eprotocol.service;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;
import static gr.scriptum.eprotocol.util.IConstants.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.openkm.ws.endpoint.PathNotFoundException;

import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.ProtocolCorrespondentDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.ProtocolDocumentDAO;
import gr.scriptum.dao.ProtocolNumberDAO;
import gr.scriptum.domain.ApplicationLog.Operation;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.DepartmentType.Name;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;
import gr.scriptum.domain.ProtocolRelation;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestAddDocumentToNode;
import gr.scriptum.eprotocol.ws.RequestDelete;
import gr.scriptum.eprotocol.ws.RequestIsNodeValid;
import gr.scriptum.eprotocol.ws.RequestMoveNode;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.RequestRenameNode;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseAddDocumentToNode;
import gr.scriptum.eprotocol.ws.ResponseIsNodeValid;
import gr.scriptum.eprotocol.ws.ResponseNewNode;
import gr.scriptum.eprotocol.ws.ResponseRenameNode;
import gr.scriptum.exception.OpenKMException;
import gr.scriptum.predicate.CorrespondentPredicate;
import gr.scriptum.predicate.CorrespondentPredicateGroup;
import gr.scriptum.predicate.CorrespondentPredicateGroup.JunctionType;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
@Service
public class ProtocolService extends BaseService {

	private static Log log = LogFactory.getLog(ProtocolService.class);

	@Autowired
	private ProtocolNumberDAO protocolNumberDAO;

	@Autowired
	private ProtocolDAO protocolDAO;

	@Autowired
	private ProtocolDocumentDAO protocolDocumentDAO;

	@Autowired
	private LoggingService loggingService;

	@Autowired
	private DistributionMethodDAO distributionMethodDAO;

	@Autowired
	private PermissionService permissionService;

	@Transactional
	public void saveIncomingProtocol(Protocol protocol, Users user, Department department, String ip,
			List<ProtocolDocument> protocolDocuments, List<ProtocolDocument> protocolDocumentsToBeDeleted,
			boolean isSubmission) throws OpenKMException {

		Date now = new Date();
		protocol.setUpdateTs(now);
		protocol.setUpdateUserId(user.getId());

		if (isSubmission) {
			log.info("Getting Protocol Number, UUID:"+protocol.getUuid());

			// assign a new protocol number, only if the protocol doesn't
			// already bear one
			if (protocol.getProtocolNumber() == null) {
				// get next availabe protocol number
				ProtocolNumber protocolNumber = protocolNumberDAO.getNextNumber(ProtocolNumberType.COMMON,
						protocol.getProtocolBook());
				log.info("Got Protocol Number, UUID/Number: "+protocol.getUuid()+"/"+protocolNumber.getNumber());
				protocol.setProtocolNumber(protocolNumber.getNumber().intValue());
			}
			 protocol.setProtocolDate(now); //replace value set during form entry

			// process transactors
			switch (protocol.getSender().getType()) {
			case ACTIVE_MEMBER:
			case INACTIVE_MEMBER:
				// create a single transactor, based on the values of the sender
				Set<ProtocolCorrespondent> transactors = new HashSet<ProtocolCorrespondent>();
				ProtocolCorrespondent sender = protocol.getSender();
				ProtocolCorrespondent transactor = new ProtocolCorrespondent(protocol,
						CorrespondentDirection.TRANSACTOR, sender.getType());
				transactor.setCode(sender.getCode());
				transactor.setDescription(sender.getDescription());
				transactor.setVatNumber(sender.getVatNumber());
				transactor.setSsn(sender.getSsn());
				transactors.add(transactor);
				protocol.setTransactors(transactors);
				break;
			default:
				break;
			}

			// Process internal recipients
			// if user doesn't bear the permission to assign internal
			// recipients, create a 'hard-coded' internal recipient, set to the
			// user's department, to facilitate searches
			Set<Permission> userPermissions = permissionService.getUserPermissions(user);
			boolean userIsNotAllowedToAssignInternalRecipients = userPermissions.stream().noneMatch(p -> p.getName()
					.equals(gr.scriptum.eprotocol.util.Permission.ASSIGN_INTERNAL_RECIPIENTS.toString()));

			if (userIsNotAllowedToAssignInternalRecipients && protocol.getInternalRecipients().isEmpty()) {
				String defaultInternalRecipientDistributionMethodCode = parameterDAO
						.getAsString(IConstants.PARAM_DEFAULT_INTERNAL_RECIPIENT_DISTRIBUTION_METHOD_CODE);
				DistributionMethod defaultInternalRecipientDistributionMethod = null;
				if (defaultInternalRecipientDistributionMethodCode != null) {
					defaultInternalRecipientDistributionMethod = new DistributionMethod();
					defaultInternalRecipientDistributionMethod.setCode(defaultInternalRecipientDistributionMethodCode);
					List<DistributionMethod> results = distributionMethodDAO
							.findByExample(defaultInternalRecipientDistributionMethod);
					if (results.size() != 1) {
						throw new RuntimeException("No default distribution method set for internal recipients");
					}
					defaultInternalRecipientDistributionMethod = results.get(0);
				}

				ProtocolCorrespondent internalRecipient = getInternalRecipient(department, protocol, defaultInternalRecipientDistributionMethod);
				internalRecipient.setDeliveryDate(DateUtils.truncate(protocol.getProtocolDate(), Calendar.DATE));
				internalRecipient.setAttachedDeliveryDate(protocol.getProtocolDate());
				protocol.getProtocolCorrespondents().add(internalRecipient);
			}
		}

		if (protocol.getId() == null) { // new protocol, create
			log.info("Saving Protocol, UUID/number/direction:"+protocol.getUuid()+"/"+protocol.getProtocolNumber()+"/"+protocol.getDirection());

			protocol.setCreateDt(now);
			protocol.setCreateUserId(user.getId());

			// set creator department
			ProtocolCorrespondent creator = new ProtocolCorrespondent(protocol, CorrespondentDirection.CREATOR, null);
			creator.setUser(user);
			creator.setDepartment(department);
			protocol.setCreator(creator);

			// Create relations
			for (ProtocolRelation protocolRelation : protocol.getProtocolRelationsAsSource()) {
				protocolRelation.setCreateDt(now);
				protocolRelation.setCreateUserId(user.getId());
				protocolRelation.setUpdateTs(now);
				protocolRelation.setUpdateUserId(user.getId());
			}

			for (ProtocolCorrespondent protocolCorrespondent : protocol.getProtocolCorrespondents()) {
				protocolCorrespondent.setCreateDt(now);
				protocolCorrespondent.setUpdateTs(now);
				protocolCorrespondent.setCreateUserId(user.getId());
				protocolCorrespondent.setUpdateUserId(user.getId());
			}

			/* local database actions */
			protocolDAO.makePersistent(protocol);

			// Create documents
			for (ProtocolDocument document : protocolDocuments) {
				document.setProtocol(protocol);
				document.setCreateDt(now);
				document.setUpdateTs(now);
				document.setCreateUserId(user.getId());
				document.setUpdateUserId(user.getId());
				protocolDocumentDAO.makePersistent(document);
				protocol.getProtocolDocuments().add(document);
			}

		} else { // existing (ie. pending) protocol
			log.info("Updating Protocol, UUID/number/direction:"+protocol.getUuid()+"/"+protocol.getProtocolNumber()+"/"+protocol.getDirection());
			
			// update relations
			for (ProtocolRelation protocolRelation : protocol.getProtocolRelationsAsSource()) {
				if (protocolRelation.getId() == null) {
					protocolRelation.setCreateDt(now);
					protocolRelation.setCreateUserId(user.getId());
				}
				protocolRelation.setUpdateTs(now);
				protocolRelation.setUpdateUserId(user.getId());
			}

			for (ProtocolCorrespondent protocolCorrespondent : protocol.getProtocolCorrespondents()) {
				if (protocolCorrespondent.getId() == null) {
					protocolCorrespondent.setCreateDt(now);
					protocolCorrespondent.setCreateUserId(user.getId());
					protocolCorrespondent.setUpdateTs(now);
					protocolCorrespondent.setUpdateUserId(user.getId());
				} else {
					// only set user and date info for 'dirty' (i.e. modified)
					// correspondents
					if (protocolCorrespondent.getDirty() != null && protocolCorrespondent.getDirty() == Boolean.TRUE) {
						protocolCorrespondent.setUpdateTs(now);
						protocolCorrespondent.setUpdateUserId(user.getId());
					}
				}
			}

			/* local database actions */
			protocolDAO.update(protocol);

			List<ProtocolDocument> newlyAddedDocuments = new LinkedList<ProtocolDocument>();
			List<ProtocolDocument> existingDocuments = new LinkedList<ProtocolDocument>();
			for (ProtocolDocument document : protocolDocuments) {
				if (document.getId() != null) { // existing document, update
					document.setUpdateTs(now);
					document.setUpdateUserId(user.getId());
					protocolDocumentDAO.update(document);
					existingDocuments.add(document);

				} else {// Create newly added documents
					document.setProtocol(protocol);
					document.setCreateDt(now);
					document.setUpdateTs(now);
					document.setCreateUserId(user.getId());
					document.setUpdateUserId(user.getId());
					protocolDocumentDAO.makePersistent(document);
					protocol.getProtocolDocuments().add(document);
					newlyAddedDocuments.add(document);
				}
			}

			// Delete documents marked respectively
			for (ProtocolDocument document : protocolDocumentsToBeDeleted) {
				protocolDocumentDAO.refresh(document);
				protocolDocumentDAO.delete(document);
				protocol.getProtocolDocuments().remove(document);
			}

			// log newly added documents
			for (ProtocolDocument protocolDocument : newlyAddedDocuments) {
				loggingService.success(Operation.ADD_DOCUMENT, user, protocol, protocolDocument.getDocumentName());
			}

			// log deleted documents
			for (ProtocolDocument protocolDocument : protocolDocumentsToBeDeleted) {
				loggingService.success(Operation.DELETE_DOCUMENT, user, protocol, protocolDocument.getDocumentName());
			}
		}
		// throw new RuntimeException("ARTIFICAL ERROR");
	}

	@Transactional
	public void uploadProtocolDocuments(Protocol protocol, Users user, String ip,
			List<ProtocolDocument> protocolDocuments) throws OpenKMException {

		// communicate with OpenKM only if there are any documents to store
		// there
		if (protocolDocuments.isEmpty()) {
			return;
		}

		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

		String folderPath = OKM_ROOT_FOLDER + OKM_FOLDER_DELIMITER + protocol.getProtocolBook().getProtocolSeries()
				+ OKM_PROTOCOL_NUMBER_DELIMITER + protocol.getProtocolBook().getId() + OKM_FOLDER_DELIMITER
				+ protocol.getProtocolNumber();

		/* OpenKM actions */

		// log into OpenKM
		String okmToken = getOkmToken();

		// create protocol node (if it doesn't already
		// exist)
		RequestIsNodeValid requestIsNodeValid = new RequestIsNodeValid(user.getUsername(), user.getId(), ip,
				IConstants.SYSTEM_NAME, okmToken);
		requestIsNodeValid.setFolderPath(folderPath);

		ResponseIsNodeValid responseIsNodeValid = okmDispatcher.isNodeValid(requestIsNodeValid);
		if (responseIsNodeValid.isError()) {
			log.error("Error was:" + responseIsNodeValid.toString());
			throw new OpenKMException(responseIsNodeValid.toString());
		}

		Boolean isNodeValid = responseIsNodeValid.getValid();
		if (!isNodeValid) {
			RequestNewNode requestNewNode = new RequestNewNode(user.getUsername(), user.getId(), ip,
					IConstants.SYSTEM_NAME, okmToken);
			requestNewNode.setFolderPath(folderPath);
			ResponseNewNode newNodeResponse = okmDispatcher.createNewNode(requestNewNode);
			if (newNodeResponse.isError()) {
				log.error("Error was:" + newNodeResponse.toString());
				throw new OpenKMException(newNodeResponse.toString());
			}
		}

		boolean errorsFound = false;
		for (ProtocolDocument document : protocolDocuments) {
			RequestAddDocumentToNode request = new RequestAddDocumentToNode(user.getUsername(), user.getId(), ip,
					IConstants.SYSTEM_NAME, okmToken);
			request.setNodePath(folderPath);
			request.setDocument(document);
			ResponseAddDocumentToNode response = okmDispatcher.addDocumentToNode(request);

			if (response.isError()) {
				log.error(response.toString());
				errorsFound = true;
				break;
			}
			// update local document, storing UUID and size
			protocolDocumentDAO.update(document);
		}

		// log out of OpenKM
		try {
			clearOkmToken(user, okmToken);
		} catch (Exception e) {
			log.error(e);
			// swallow
		}

		if (errorsFound) {
			throw new OpenKMException("Errors during document upload");
		}
	}

	@Transactional
	@Deprecated
	public void movePendingProtocolDocumentsToSubmitted(Protocol protocol, Users user, String ip,
			List<ProtocolDocument> protocolDocuments) throws OpenKMException {

		String okmNodePending = parameterDAO.getAsString(protocol.getDirection().equals(Direction.INCOMING)
				? IConstants.PARAM_OKM_NODE_PENDING_INCOMING : IConstants.PARAM_OKM_NODE_PENDING_OUTGOING);
		String okmNode = parameterDAO.getAsString(protocol.getDirection().equals(Direction.INCOMING)
				? IConstants.PARAM_OKM_NODE_INCOMING : IConstants.PARAM_OKM_NODE_OUTGOING);

		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

		/* OpenKM actions */

		// log into OpenKM
		String okmToken = getOkmToken();

		// move 'pending' OpenKM node to 'incoming'
		RequestMoveNode requestMoveNode = new RequestMoveNode(user.getUsername(), user.getId(), ip,
				IConstants.SYSTEM_NAME, okmToken);
		requestMoveNode.setOldPath(okmNodePending + IConstants.OKM_FOLDER_DELIMITER + protocol.getId());
		requestMoveNode
				.setNewPath(okmNode + IConstants.OKM_FOLDER_DELIMITER + protocol.getProtocolBook().getProtocolSeries()
						+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER + protocol.getProtocolBook().getProtocolYear());

		Response responseMoveNode = okmDispatcher.moveNode(requestMoveNode);
		if (responseMoveNode.isError()) {
			log.error(responseMoveNode.toString());
			throw new OpenKMException(responseMoveNode.toString());
		}

		// rename node, based on assigned protocol number
		RequestRenameNode requestRenameNode = new RequestRenameNode(user.getUsername(), user.getId(), ip,
				IConstants.SYSTEM_NAME, okmToken);
		requestRenameNode.setOldName(okmNode + IConstants.OKM_FOLDER_DELIMITER
				+ protocol.getProtocolBook().getProtocolSeries() + IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
				+ protocol.getProtocolBook().getProtocolYear() + IConstants.OKM_FOLDER_DELIMITER + protocol.getId());
		requestRenameNode.setNewName(protocol.getProtocolNumber().toString());

		ResponseRenameNode responseRenameNode = okmDispatcher.renameNode(requestRenameNode);
		if (responseRenameNode.isError()) {
			log.error(responseRenameNode.toString());
			throw new OpenKMException(responseRenameNode.toString());
		}

		// update local protocol documents with new path
		for (ProtocolDocument document : protocolDocuments) {
			String path = document.getOkmPath();
			path = path.replaceFirst(okmNodePending + IConstants.OKM_FOLDER_DELIMITER + protocol.getId(),
					okmNode + IConstants.OKM_FOLDER_DELIMITER + protocol.getProtocolBook().getProtocolSeries()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER + protocol.getProtocolBook().getProtocolYear()
							+ IConstants.OKM_FOLDER_DELIMITER + protocol.getProtocolNumber());
			document.setOkmPath(path);
			protocolDocumentDAO.update(document);
		}
	}

	public void deleteUploadedDocuments(List<ProtocolDocument> protocolDocumentsToBeDeleted, Users user, String ip)
			throws OpenKMException {

		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

		/* OpenKM actions */

		// log into OpenKM
		String okmToken = getOkmToken();

		// delete documents marked respectively from OpenKM
		boolean errorsFound = false;
		for (ProtocolDocument document : protocolDocumentsToBeDeleted) {

			RequestDelete requestDelete = new RequestDelete(user.getUsername(), user.getId(), ip,
					IConstants.SYSTEM_NAME, okmToken);
			requestDelete.setDirectory(false);
			requestDelete.setUuid(document.getOkmUuid());

			Response responseDelete = okmDispatcher.delete(requestDelete);
			if (responseDelete.isError()) {
				log.error(responseDelete.toString());
				if (responseDelete.getError() instanceof PathNotFoundException) {
					// swallow already deleted documents
					continue;
				}
				errorsFound = true;
			}
		}

		// log out of OpenKM
		try {
			clearOkmToken(user, okmToken);
		} catch (Exception e) {
			log.error(e);
			// swallow
		}

		if (errorsFound) {
			throw new OpenKMException("Errors during document delete");
		}
	}

	@Transactional
	public void saveOutgoingProtocol(Protocol protocol, Users user, Department department, String ip,
			List<ProtocolDocument> protocolDocuments, List<ProtocolDocument> protocolDocumentsToBeDeleted,
			boolean isSubmission) throws OpenKMException {

		Date now = new Date();
		protocol.setUpdateTs(now);
		protocol.setUpdateUserId(user.getId());

		if (isSubmission) {
			log.info("Getting Protocol Number, UUID:"+protocol.getUuid());;

			// assign a new protocol number, only if the protocol doesn't
			// already bear one
			if (protocol.getProtocolNumber() == null) {
				ProtocolNumber protocolNumber = null;
				protocolNumber = protocolNumberDAO.getNextNumber(ProtocolNumberType.COMMON, protocol.getProtocolBook());
				log.info("Got Protocol Number, UUID/Number: "+protocol.getUuid()+"/"+protocolNumber.getNumber());
				protocol.setProtocolNumber(protocolNumber.getNumber().intValue());
			}
			 protocol.setProtocolDate(now); //replace value set during form entry
		}

		if (protocol.getId() == null) { // new protocol, create
			log.info("Updating Protocol, UUID/number/direction:"+protocol.getUuid()+"/"+protocol.getProtocolNumber()+"/"+protocol.getDirection());
			
			protocol.setCreateDt(now);
			protocol.setCreateUserId(user.getId());

			// set creator department
			ProtocolCorrespondent creator = new ProtocolCorrespondent(protocol, CorrespondentDirection.CREATOR, null);
			creator.setUser(user);
			creator.setDepartment(department);
			protocol.setCreator(creator);

			// set department as protocol sender
			protocol.setSender(department);

			protocol.setProtocolDocuments(new HashSet<ProtocolDocument>());

			// Create relations
			for (ProtocolRelation protocolRelation : protocol.getProtocolRelationsAsSource()) {
				protocolRelation.setSourceProtocol(protocol);
				protocolRelation.setCreateDt(now);
				protocolRelation.setCreateUserId(user.getId());
				protocolRelation.setUpdateTs(now);
				protocolRelation.setUpdateUserId(user.getId());
			}

			for (ProtocolCorrespondent protocolCorrespondent : protocol.getProtocolCorrespondents()) {
				protocolCorrespondent.setCreateDt(now);
				protocolCorrespondent.setCreateUserId(user.getId());
				protocolCorrespondent.setUpdateTs(now);
				protocolCorrespondent.setUpdateUserId(user.getId());
			}

			/* local database actions */
			protocolDAO.makePersistent(protocol);

			// Create documents
			for (ProtocolDocument document : protocolDocuments) {
				document.setProtocol(protocol);
				document.setCreateDt(now);
				document.setCreateUserId(user.getId());
				document.setUpdateTs(now);
				document.setUpdateUserId(user.getId());
				protocolDocumentDAO.makePersistent(document);
				protocol.getProtocolDocuments().add(document);
			}

		} else { // existing (ie. pending) protocol
			log.info("Updating Protocol, number/direction:"+protocol.getProtocolNumber()+"/"+protocol.getDirection());
			
			// update relations
			for (ProtocolRelation protocolRelation : protocol.getProtocolRelationsAsSource()) {
				if (protocolRelation.getId() == null) {
					protocolRelation.setSourceProtocol(protocol);
					protocolRelation.setCreateDt(now);
					protocolRelation.setCreateUserId(user.getId());
				}
				protocolRelation.setUpdateTs(now);
				protocolRelation.setUpdateUserId(user.getId());
			}

			for (ProtocolCorrespondent protocolCorrespondent : protocol.getProtocolCorrespondents()) {
				if (protocolCorrespondent.getId() == null) {
					protocolCorrespondent.setCreateDt(now);
					protocolCorrespondent.setCreateUserId(user.getId());
				}
				protocolCorrespondent.setUpdateTs(now); // TODO: set only if
														// dirty
				protocolCorrespondent.setUpdateUserId(user.getId());
			}

			/* local database actions */
			protocolDAO.update(protocol);

			// process newly added & existing documents
			List<ProtocolDocument> newlyAddedDocuments = new LinkedList<ProtocolDocument>();
			List<ProtocolDocument> existingDocuments = new LinkedList<ProtocolDocument>();
			for (ProtocolDocument document : protocolDocuments) {
				if (document.getId() != null) { // existing document, update
					document.setUpdateTs(now);
					document.setUpdateUserId(user.getId());
					protocolDocumentDAO.update(document);
					existingDocuments.add(document);

				} else {// new document, create
					document.setProtocol(protocol);
					document.setCreateDt(now);
					document.setUpdateTs(now);
					document.setCreateUserId(user.getId());
					document.setUpdateUserId(user.getId());
					protocolDocumentDAO.makePersistent(document);
					protocol.getProtocolDocuments().add(document);
					newlyAddedDocuments.add(document);
				}
			}

			// Delete documents marked respectively
			for (ProtocolDocument document : protocolDocumentsToBeDeleted) {
				protocolDocumentDAO.refresh(document);
				protocolDocumentDAO.delete(document);
				protocol.getProtocolDocuments().remove(document);
			}

			// log newly added documents
			for (ProtocolDocument protocolDocument : newlyAddedDocuments) {
				loggingService.success(Operation.ADD_DOCUMENT, user, protocol, protocolDocument.getDocumentName());
			}

			// log deleted documents
			for (ProtocolDocument protocolDocument : protocolDocumentsToBeDeleted) {
				loggingService.success(Operation.DELETE_DOCUMENT, user, protocol, protocolDocument.getDocumentName());
			}
		}
		// throw new RuntimeException("ARTIFICAL ERROR");
	}

	@Transactional
	public Protocol updateProtocol(Protocol protocol, Users user) {
		Date now = new Date();
		protocol.setUpdateTs(now);
		protocol.setUpdateUserId(user.getId());

		return protocolDAO.merge(protocol);
	}

	@Transactional(readOnly = true)
	public List<Protocol> findAlreadySubmittedProtocols(Protocol protocol, Department creatorDepartment) {

		if (protocol.getIncomingProtocolNumber() == null || protocol.getIncomingDate() == null) {
			return new ArrayList<Protocol>();
		}

		List<ProtocolBook> protocolBooks = new ArrayList<ProtocolBook>();
		protocolBooks.add(protocol.getProtocolBook());
		CorrespondentPredicate senderPredicate = new CorrespondentPredicate(protocol.getSender());
		senderPredicate.setDirection(CorrespondentDirection.SENDER);

		ProtocolCorrespondent creator = new ProtocolCorrespondent(protocol, CorrespondentDirection.CREATOR, null);
		creator.setDepartment(creatorDepartment);
		CorrespondentPredicate creatorPredicate = new CorrespondentPredicate(creator);
		creatorPredicate.setDirection(CorrespondentDirection.CREATOR);

		List<CorrespondentPredicate> correspondentPredicates = new ArrayList<CorrespondentPredicate>();
		correspondentPredicates.add(senderPredicate);
		correspondentPredicates.add(creatorPredicate);
		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicates,
				JunctionType.AND);

		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);

		return protocolDAO.search(Direction.INCOMING, null, null, protocol.getIncomingProtocolNumber(),
				protocol.getIncomingDate(), protocol.getIncomingDate(), null, null, null, null, null, null, null, null,
				null, null, false, false, protocolBooks, null, null, predicateGroups, null, Order.desc("id"));
	}

	@Transactional
	public void routeProtocol(Protocol protocol, List<ProtocolCorrespondent> selectedRecipients, Date routingDate,
			Users user) {

		routingDate = DateUtils.truncate(routingDate, Calendar.DATE);
		Date now = new Date();

		protocol = protocolDAO.merge(protocol);
		switch (protocol.getDirection()) {
		case INCOMING:
			Set<ProtocolCorrespondent> internalRecipients = protocol.getInternalRecipients();
			for (ProtocolCorrespondent internalRecipient : internalRecipients) {
				for (ProtocolCorrespondent selectedRecipient : selectedRecipients) {
					if (selectedRecipient.getId().equals(internalRecipient.getId())
							&& internalRecipient.getRoutingDate() == null) {
						internalRecipient.setRoutingDate(routingDate);
						internalRecipient.setUpdateUserId(user.getId());
						internalRecipient.setUpdateTs(now);
					}
				}
			}
			break;
		case OUTGOING:
			Set<ProtocolCorrespondent> recipients = protocol.getRecipients();
			for (ProtocolCorrespondent recipient : recipients) {
				for (ProtocolCorrespondent selectedRecipient : selectedRecipients) {
					if (selectedRecipient.getId().equals(recipient.getId()) && recipient.getRoutingDate() == null) {
						recipient.setRoutingDate(routingDate);
						recipient.setUpdateUserId(user.getId());
						recipient.setUpdateTs(now);
					}
				}
			}
			break;
		}
	}

	@Transactional
	public void batchRouteProtocols(List<Protocol> batchRouteProtocols, List<Department> batchRouteRecipients,
			Date routingDate, Users user) {

		routingDate = DateUtils.truncate(routingDate, Calendar.DATE);
		Date now = new Date();

		for (Protocol protocol : batchRouteProtocols) {
			protocol = protocolDAO.merge(protocol);

			switch (protocol.getDirection()) {
			case INCOMING:
				Set<ProtocolCorrespondent> internalRecipients = protocol.getInternalRecipients();
				for (ProtocolCorrespondent internalRecipient : internalRecipients) {
					if (batchRouteRecipients.contains(internalRecipient.getDepartment())
							&& internalRecipient.getRoutingDate() == null) {
						internalRecipient.setRoutingDate(routingDate);
						internalRecipient.setUpdateUserId(user.getId());
						internalRecipient.setUpdateTs(now);
					}
				}
				break;
			case OUTGOING:
				Set<ProtocolCorrespondent> recipients = protocol.getRecipients();
				for (ProtocolCorrespondent recipient : recipients) {
					if (recipient.getType().equals(CorrespondentType.DEPARTMENT)
							&& batchRouteRecipients.contains(recipient.getDepartment())
							&& recipient.getRoutingDate() == null) {
						recipient.setRoutingDate(routingDate);
						recipient.setUpdateUserId(user.getId());
						recipient.setUpdateTs(now);
					}
				}
				break;
			}
		}
	}

	@Transactional
	public void receiveProtocols(List<ProtocolCorrespondent> recipients, List<ProtocolCorrespondent> assignees,
			Users user) {
		Date now = new Date();
		Set<Protocol> protocols = new HashSet<Protocol>();
		for (ProtocolCorrespondent recipient : recipients) {
			protocols.add(recipient.getProtocol());
		}

		for (Protocol protocol : protocols) {
			Set<ProtocolCorrespondent> protocolAssignees = new HashSet<ProtocolCorrespondent>();
			for (ProtocolCorrespondent assignee : assignees) {
				ProtocolCorrespondent assigneeCopy = new ProtocolCorrespondent();
				try {
					PropertyUtils.copyProperties(assigneeCopy, assignee);
					assigneeCopy.setProtocol(protocol);
					assigneeCopy.setCreateDt(now);
					assigneeCopy.setCreateUserId(user.getId());
					assigneeCopy.setUpdateTs(now);
					assigneeCopy.setUpdateUserId(user.getId());
					protocolAssignees.add(assigneeCopy);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					log.error(e);
					continue;
				}
			}
			protocol.setAssignees(protocolAssignees);
			protocolDAO.merge(protocol);
		}
	}

	@Transactional
	public void replaceDepartmentAssignees(Protocol protocol, List<Department> departments,
			List<ProtocolCorrespondent> departmentAssignees, Users user) {
		protocol = protocolDAO.merge(protocol);
		Set<ProtocolCorrespondent> assignees = protocol.getAssignees();
		// go through existing protocol assignees and remove those matching any
		// of the passed on departments
		Iterator<ProtocolCorrespondent> iterator = assignees.iterator();
		while (iterator.hasNext()) {
			ProtocolCorrespondent assignee = iterator.next();
			if (departments.contains(assignee.getDepartment())) {
				iterator.remove();
			}
		}
		// add updated assignees to the 'cleansed' list
		assignees.addAll(departmentAssignees);
		Date now = new Date();
		for (ProtocolCorrespondent departmentAssignee : departmentAssignees) {
			departmentAssignee.setUpdateTs(now);
			departmentAssignee.setUpdateUserId(user.getId());
		}
		protocol.setAssignees(assignees);
		protocolDAO.merge(protocol);
	}

	private Date getCurrentDate(Protocol protocol) {
		return DateUtils.truncate(protocol.getProtocolDate(), Calendar.DATE);
	}

	public Date getRoutingDate(ProtocolCorrespondent recipient, Department creatorDepartment) {

		Date routingDate = null;
		Protocol protocol = recipient.getProtocol();
		Assert.notNull(protocol.getDirection(), "Invalid protocol direction");

		DepartmentType.Name type = DepartmentType.Name.valueOf(creatorDepartment.getDepartmentType().getName());
		switch (type) {
		case CENTRAL_PROTOCOL:
		case CENTRAL:
			switch (protocol.getDirection()) {
			case INCOMING:
				log.debug(type + "/INCOMING -> routing date: current");
				routingDate = getCurrentDate(protocol);
				break;
			case OUTGOING:
				if (recipient.getDistributionMethod().getAutoFillRoutingDate()) {
					log.debug(type + "/OUTGOING/Auto-fill distribution method -> routing date: current");
					routingDate = getCurrentDate(protocol);
				} else {
					log.debug(type + "/OUTGOING/Manual-fill distribution method -> routing date: null");
					routingDate = null;
				}
				break;
			}
			break;
		case REGIONAL:
		case BRANCH:
			switch (protocol.getDirection()) {
			case INCOMING:
				log.debug(type + "/INCOMING -> routing date: current");
				routingDate = getCurrentDate(protocol);
				break;
			case OUTGOING:
				if (recipient.getType().equals(DEPARTMENT)) {
					Name recipientDeparmentType = DepartmentType.Name
							.valueOf(recipient.getDepartment().getDepartmentType().getName());
					switch (recipientDeparmentType) {
					case CENTRAL:
						if (recipient.getDistributionMethod().getAutoFillRoutingDate()) {
							log.debug(type + "/OUTGOING/Recipient type:" + recipientDeparmentType
									+ "/Auto-fill distribution method -> routing date: current");
							routingDate = getCurrentDate(protocol);
						} else {
							log.debug(type + "/OUTGOING/Recipient type:" + recipientDeparmentType
									+ "/Manual-fill distribution method -> routing date: null");
							routingDate = null;
						}
						break;
					case REGIONAL:
					case BRANCH:
					case CENTRAL_PROTOCOL:
						log.debug(type + "/OUTGOING/Recipient type:" + recipientDeparmentType
								+ " -> routing date: current");
						routingDate = getCurrentDate(protocol);
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid Recipient Department type for routing date: " + recipientDeparmentType);
					}
				} else {
					log.debug(type + "/OUTGOING/Non department-> routing date: current");
					routingDate = getCurrentDate(protocol);
				}
				break;
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid Creator Department type for routing date: " + type);
		}

		return routingDate;
	}

	public ProtocolCorrespondent getRecipientActiveMember(String code, String description, Protocol protocol,
			Department creatorDepartment, DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, ACTIVE_MEMBER);
		protocolCorrespondent.setCode(code);
		protocolCorrespondent.setDescription(description);
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	public ProtocolCorrespondent getRecipientInactiveMember(String code, String description, Protocol protocol,
			Department creatorDepartment, DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, INACTIVE_MEMBER);
		protocolCorrespondent.setCode(code);
		protocolCorrespondent.setDescription(description);
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	public ProtocolCorrespondent getRecipient(Contact contact, Protocol protocol, Department creatorDepartment,
			DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, CorrespondentType.CONTACT);
		protocolCorrespondent.setContact(contact);
		protocolCorrespondent.setDescription(contact.getFullName());
		protocolCorrespondent.setVatNumber(contact.getVatNumber());
		protocolCorrespondent.setSsn(contact.getSsn());
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	public ProtocolCorrespondent getRecipient(UserHierarchy userHierarchy, Protocol protocol,
			Department creatorDepartment, DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, CorrespondentType.EMPLOYEE);
		protocolCorrespondent.setContact(userHierarchy.getContact());
		protocolCorrespondent.setDepartment(userHierarchy.getDepartment());
		protocolCorrespondent.setDescription(userHierarchy.getContact().getFullName());
		protocolCorrespondent.setCode(userHierarchy.getContact().getCode());
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	public ProtocolCorrespondent getRecipient(Department department, Protocol protocol, Department creatorDepartment,
			DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, CorrespondentType.DEPARTMENT);
		protocolCorrespondent.setDepartment(department);
		protocolCorrespondent.setDescription(department.getName() + " (" + department.getCode() + ")");
		protocolCorrespondent.setCode(department.getCode());
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	public ProtocolCorrespondent getRecipient(Company company, Protocol protocol, Department creatorDepartment,
			DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, CorrespondentType.COMPANY);
		protocolCorrespondent.setCompany(company);
		protocolCorrespondent.setCode(company.getCode());
		protocolCorrespondent.setDescription(company.getName());
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	public ProtocolCorrespondent getRecipient(String other, Protocol protocol, Department creatorDepartment,
			DistributionMethod distributionMethod, CorrespondentAction action) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.RECIPIENT, CorrespondentType.OTHER);
		protocolCorrespondent.setDescription(other);
		protocolCorrespondent.setAction(action);
		protocolCorrespondent.setDistributionMethod(distributionMethod);
		protocolCorrespondent.setDispatchDate(protocol.getProtocolDate());
		protocolCorrespondent.setRoutingDate(getRoutingDate(protocolCorrespondent, creatorDepartment));
		return protocolCorrespondent;
	}

	@Transactional(readOnly = true)
	public DistributionMethod getProtocolRecipientDefaultDistributionMethod() {
		String defaultRecipientDistributionMethodCode = parameterDAO
				.getAsString(IConstants.PARAM_DEFAULT_RECIPIENT_DISTRIBUTION_METHOD_CODE);
		if (defaultRecipientDistributionMethodCode == null) {
			return null;
		}
		DistributionMethod example = new DistributionMethod();
		example.setCode(defaultRecipientDistributionMethodCode);
		List<DistributionMethod> results = distributionMethodDAO.findByExample(example);
		if (results.size() != 1) {
			return null;
		}
		return results.get(0);
	}

	public boolean recipientFoundInList(List<ProtocolCorrespondent> recipients, ProtocolCorrespondent recipient) {
		// ACTIVE_MEMBER, INACTIVE_MEMBER, EMPLOYEE, DEPARTMENT, COMPANY,
		// CONTACT, OTHER
		switch (recipient.getType()) {
		case ACTIVE_MEMBER:
			log.debug("active_member");
			return recipients.stream().anyMatch(
					r -> r.getType().equals(ACTIVE_MEMBER) && r.getDescription().equals(recipient.getDescription()));
		case INACTIVE_MEMBER:
			log.debug("inactive_member");
			return recipients.stream().anyMatch(
					r -> r.getType().equals(INACTIVE_MEMBER) && r.getDescription().equals(recipient.getDescription()));
		case EMPLOYEE:
			log.debug("employee");
			return recipients.stream()
					.anyMatch(r -> r.getType().equals(EMPLOYEE) && r.getContact().equals(recipient.getContact())
							&& r.getDepartment().equals(recipient.getDepartment()));
		case DEPARTMENT:
			log.debug("department");
			return recipients.stream().anyMatch(
					r -> r.getType().equals(DEPARTMENT) && r.getDepartment().equals(recipient.getDepartment()));
		case COMPANY:
			log.debug("company");
			return recipients.stream()
					.anyMatch(r -> r.getType().equals(COMPANY) && r.getCompany().equals(recipient.getCompany()));
		case CONTACT:
			log.debug("contact");
			return recipients.stream()
					.anyMatch(r -> r.getType().equals(CONTACT) && r.getContact().equals(recipient.getContact()));
		case OTHER:
			log.debug("other");
			return recipients.stream()
					.anyMatch(r -> r.getType().equals(OTHER) && r.getDescription().equals(recipient.getDescription()));
		default:
			return false;
		}
	}

	public boolean recipientsContainAnyInternalTypes(List<ProtocolCorrespondent> recipients) {
		CorrespondentType[] internalTypes = { EMPLOYEE, DEPARTMENT, COMPANY, CONTACT, OTHER };
		return recipients.stream().anyMatch(r -> ArrayUtils.contains(internalTypes, r.getType()));
	}

	public boolean internalRecipientFoundInList(ProtocolCorrespondent internalRecipient,
			List<ProtocolCorrespondent> internalRecipients) {
		boolean found = false;
		for (ProtocolCorrespondent existingInternalRecipient : internalRecipients) {
			found = found | existingInternalRecipient.getDepartment().equals(internalRecipient.getDepartment());
		}
		return found;
	}

	public ProtocolCorrespondent getInternalRecipient(Department department, Protocol protocol,
			DistributionMethod distributionMethod) {
		ProtocolCorrespondent internalRecipient = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.INTERNAL_RECIPIENT, CorrespondentType.DEPARTMENT);
		internalRecipient.setAction(CorrespondentAction.TO);
		internalRecipient.setDistributionMethod(distributionMethod);
		internalRecipient.setDepartment(department);
		internalRecipient.setCode(department.getCode());
		internalRecipient.setDescription(department.getName() + " (" + department.getCode() + ")");
		internalRecipient.setRoutingDate(getRoutingDate(internalRecipient, department));
		return internalRecipient;
	}

	public List<ProtocolCorrespondent> getInternalRecipientsApplicableForRouting(
			List<ProtocolCorrespondent> internalRecipients) {
		boolean isValid = internalRecipients.stream().allMatch(
				ir -> ir.getType().equals(DEPARTMENT) & ir.getProtocol().getDirection().equals(Direction.INCOMING));
		if (!isValid) {
			throw new IllegalArgumentException(
					"All internal recipients must be departments and must belong to an incoming protocol");
		}
		return internalRecipients.stream().filter(ir -> ir.getRoutingDate() == null)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public List<ProtocolCorrespondent> getRecipientsApplicableForRouting(List<ProtocolCorrespondent> recipients) {
		boolean isValid = recipients.stream()
				.allMatch(ir -> ir.getProtocol().getDirection().equals(Direction.OUTGOING));
		if (!isValid) {
			throw new IllegalArgumentException("All recipients must belong to an incoming protocol");
		}
		return recipients.stream()
				.filter(r -> r.getType().equals(CorrespondentType.DEPARTMENT) && r.getRoutingDate() == null)
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
