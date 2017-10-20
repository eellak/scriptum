/**
 * 
 */
package gr.scriptum.eprotocol.service;

import static gr.scriptum.eprotocol.util.IConstants.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.dao.ProtocolNumberDAO;
import gr.scriptum.dao.ResourceDAO;
import gr.scriptum.dao.UserAssignmentDAO;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.Resource;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Resource.ResourceType;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestDelete;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.RequestRenameNode;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseNewNode;
import gr.scriptum.eprotocol.ws.ResponseRenameNode;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class ProtocolBookService extends BaseService {

	private static Log log = LogFactory.getLog(ProtocolBookService.class);

	@Autowired
	private ProtocolBookDAO dao;

	@Autowired
	private ProtocolNumberDAO protocolNumberDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private UserAssignmentDAO userAssignmentDAO;

	@Autowired
	private ParameterDAO parameterDAO;

	@Transactional
	public ProtocolBook create(ProtocolBook entity, Users user, String ip, List<UserAssignment> assignments)
			throws OpenKMException {

		entity = dao.makePersistent(entity);
		log.info(entity.getId());

		// create an associated protocol number counter
		ProtocolNumber protocolNumber = protocolNumberDAO.createProtocolNumber(entity);

		// create an associated resource (for access control)
		Resource resource = new Resource();
		resource.setType(ResourceType.ENTITY);
		resource.setResourceClass(ProtocolBook.class.getName());
		resource.setActive(Boolean.TRUE);
		resource.setKeyCode(ProtocolBook.class.getSimpleName());
		resource.setValue(entity.getId().toString());

		resource = resourceDAO.makePersistent(resource);

		// create user-resource-role assignments
		if (assignments != null) {
			for (UserAssignment userAssignment : assignments) {
				userAssignmentDAO.makePersistent(userAssignment);
			}
		}

		Boolean isDocumentUploadFunctionalityEnabled = parameterDAO
				.getAsBoolean(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY);

		if (isDocumentUploadFunctionalityEnabled) {

			/* OpenKM actions */
			// TODO: improve OpenKM error handling
			// log into OpenKM
			String okmToken = getOkmToken();

			// create Protocol Book folders
			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			// create book folder
			RequestNewNode requestNewNode = new RequestNewNode(user.getUsername(), user.getId(), ip,
					IConstants.SYSTEM_NAME, okmToken);
			requestNewNode
					.setFolderPath(OKM_ROOT_FOLDER + OKM_FOLDER_DELIMITER + entity.getProtocolSeries()
							+ OKM_PROTOCOL_NUMBER_DELIMITER + entity.getId());
			ResponseNewNode newNodeResponse = okmDispatcher.createNewNode(requestNewNode);

			// log out of OpenKM
			try {
				clearOkmToken(user, okmToken);
			} catch (Exception e) {
				log.error(e);
				// swallow
			}

			if (newNodeResponse.isError()) {
				log.error(newNodeResponse.toString());
				throw new OpenKMException("Error creating book folder", newNodeResponse.getError());
			}

			entity.setProtocolPath(newNodeResponse.getDocumentFolder().getPath());
			dao.update(entity);

		}
		return entity;
	}

	@Transactional
	public void update(ProtocolBook entity, Users user, String ip, List<UserAssignment> assignments,
			Set<UserAssignment> assignmentsToBeDeleted) throws OpenKMException {

		dao.update(entity);

		// create new assignments
		for (UserAssignment userAssignment : assignments) {
			if (userAssignment.getId() == null) {
				userAssignmentDAO.makePersistent(userAssignment);
			}
		}

		// delete assignments marked accordingly
		for (UserAssignment userAssignment : assignmentsToBeDeleted) {
			userAssignmentDAO.refresh(userAssignment);
			userAssignmentDAO.delete(userAssignment);
		}
		
		Boolean isDocumentUploadFunctionalityEnabled = parameterDAO
				.getAsBoolean(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY);

		if (isDocumentUploadFunctionalityEnabled) {

			String path = entity.getProtocolPath();
			String existingName = path.substring(path.lastIndexOf(OKM_FOLDER_DELIMITER)+1, path.length());
			
			String newName = entity.getProtocolSeries()
			+ OKM_PROTOCOL_NUMBER_DELIMITER + entity.getId();
			
			if(existingName.equals(newName)) {
				//no renaming needs to be done
				return;
			}
			
			/* OpenKM actions */
			// TODO: improve OpenKM error handling
			// log into OpenKM
			String okmToken = getOkmToken();

			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			// rename book folder
			RequestRenameNode requestRenameNode = new RequestRenameNode(user.getUsername(), user.getId(), ip,
					IConstants.SYSTEM_NAME, okmToken);
			requestRenameNode.setOldName(entity.getProtocolPath());
			requestRenameNode.setNewName(newName);
			ResponseRenameNode responseRenameNode = okmDispatcher.renameNode(requestRenameNode);

			// log out of OpenKM
			try {
				clearOkmToken(user, okmToken);
			} catch (Exception e) {
				log.error(e);
				// swallow
			}

			if (responseRenameNode.isError()) {
				log.error(responseRenameNode.toString());
				throw new OpenKMException("Error renaming book folder", responseRenameNode.getError());
			}

			entity.setProtocolPath(responseRenameNode.getDocumentFolder().getPath());
			dao.update(entity);

		}

	}

	@Transactional
	public void delete(ProtocolBook entity, Users user, String ip) throws OpenKMException {

		dao.refresh(entity);

		// delete associated ProtocolNumbers
		for (ProtocolNumber protocolNumber : entity.getProtocolNumbers()) {
			protocolNumberDAO.delete(protocolNumber);
		}

		// delete relevant assignments
		// TODO: add 'isDeletable' check to ProtocolBookDAO
		List<UserAssignment> assignments = userAssignmentDAO.findByEntity(ProtocolBook.class, entity.getId());
		for (UserAssignment userAssignment : assignments) {
			userAssignmentDAO.delete(userAssignment);
		}

		// delete relevant Resource
		// TODO: add 'isDeletable' check to ProtocolBookDAO
		Resource resource = resourceDAO.findEntityResource(entity.getClass(), entity.getId());
		if (resource != null) {
			resourceDAO.delete(resource);
		}

		// delete book
		dao.delete(entity);

		Boolean isDocumentUploadFunctionalityEnabled = parameterDAO
				.getAsBoolean(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY);

		if (isDocumentUploadFunctionalityEnabled) {
			/* OpenKM actions */

			// log into OpenKM
			String okmToken = getOkmToken();

			// delete Protocol Book folders

			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			// delete book folder
			RequestDelete requestDelete = new RequestDelete(user.getUsername(), user.getId(), ip,
					IConstants.SYSTEM_NAME, okmToken);
			requestDelete.setDirectory(true);
			requestDelete.setPath(OKM_ROOT_FOLDER + OKM_FOLDER_DELIMITER +  entity.getProtocolSeries()
					+ OKM_PROTOCOL_NUMBER_DELIMITER + entity.getId());
			Response responseDelete = okmDispatcher.delete(requestDelete);

			// log out of OpenKM
			try {
				clearOkmToken(user, okmToken);
			} catch (Exception e) {
				log.error(e);
				// swallow
			}

			if (responseDelete.isError()) {
				log.error(responseDelete.toString());
				throw new OpenKMException("Error deleting book folder", responseDelete.getError());
			}

		}
	}

	@Transactional(readOnly = true)
	public List<ProtocolBook> findByAssignedUser(Users user, Permission... permissions) {
		Set<ProtocolBook> assignedBooks = new HashSet<ProtocolBook>();
		List<UserAssignment> userAssignments = userAssignmentDAO.findByUserAndEntityType(user, ProtocolBook.class, permissions);
		for (UserAssignment userAssignment : userAssignments) {
			ProtocolBook protocolBook = dao.findById(Integer.parseInt(userAssignment.getResource().getValue()), false);
			assignedBooks.add(protocolBook);
		}
		return new ArrayList<ProtocolBook>(assignedBooks);
	}

}
