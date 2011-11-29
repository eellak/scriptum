/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestDelete;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseNewNode;

/**
 * @author aanagnostopoulos
 * 
 */
public class BookController extends
		GenericEntityController<ProtocolBook, ProtocolBookDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 523942398656291980L;

	private static Log log = LogFactory.getLog(BookController.class);

	public static final String PAGE = "book.zul";

	@Override
	protected void save() throws Exception {

		Integer id = getEntityId(entity);

		ProtocolBookDAO dao = initDAO();
		if (id == null) { // new entity, create
			dao.makePersistent(entity);

			/* OpenKM actions */

			// create Protocol Book folders
			ParameterDAO parameterDAO = new ParameterDAO();
			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			// create incoming book folder
			String okmNodeIncoming = parameterDAO
					.getAsString(IConstants.PARAM_OKM_NODE_INCOMING);
			RequestNewNode requestNewNode = new RequestNewNode(
					getUserInSession().getUsername(), getUserInSession()
							.getId(), getIp(), IConstants.SYSTEM_NAME,
					getOkmToken());
			requestNewNode.setFolderPath(okmNodeIncoming
					+ IConstants.OKM_FOLDER_DELIMITER
					+ ((ProtocolBook) entity).getProtocolSeries()
					+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
					+ ((ProtocolBook) entity).getProtocolYear());
			ResponseNewNode newNodeResponse = okmDispatcher
					.createNewNode(requestNewNode);

			if (newNodeResponse.isError()) {
				log.error(newNodeResponse.toString());
				throw new RuntimeException(newNodeResponse.toString());
			}

			// create outgoing book folder
			String okmNodeOutgoing = parameterDAO
					.getAsString(IConstants.PARAM_OKM_NODE_OUTGOING);
			requestNewNode = new RequestNewNode(getUserInSession()
					.getUsername(), getUserInSession().getId(), getIp(),
					IConstants.SYSTEM_NAME, getOkmToken());
			requestNewNode.setFolderPath(okmNodeOutgoing
					+ IConstants.OKM_FOLDER_DELIMITER
					+ ((ProtocolBook) entity).getProtocolSeries()
					+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
					+ ((ProtocolBook) entity).getProtocolYear());
			newNodeResponse = okmDispatcher.createNewNode(requestNewNode);

			if (newNodeResponse.isError()) {
				log.error(newNodeResponse.toString());
				throw new RuntimeException(newNodeResponse.toString());
			}

		} else {
			dao.update(entity);
		}

	}

	@Override
	protected void delete() throws Exception {

		Integer id = getEntityId(entity);

		ProtocolBookDAO dao = new ProtocolBookDAO();
		dao.deleteById(id);

		/* OpenKM actions */

		// delete Protocol Book folders
		ParameterDAO parameterDAO = new ParameterDAO();
		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

		// delete incoming book folder
		String okmNodeIncoming = parameterDAO
				.getAsString(IConstants.PARAM_OKM_NODE_INCOMING);
		RequestDelete requestDelete = new RequestDelete(getUserInSession()
				.getUsername(), getUserInSession().getId(), getIp(),
				IConstants.SYSTEM_NAME, getOkmToken());
		requestDelete.setDirectory(true);
		requestDelete.setPath(okmNodeIncoming + IConstants.OKM_FOLDER_DELIMITER
				+ ((ProtocolBook) entity).getProtocolSeries()
				+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
				+ ((ProtocolBook) entity).getProtocolYear());
		Response responseDelete = okmDispatcher.delete(requestDelete);
		if (responseDelete.isError()) {
			log.error(responseDelete.toString());
			throw new RuntimeException(responseDelete.toString());
		}

		// delete outgoing book folder
		String okmNodeOutgoing = parameterDAO
				.getAsString(IConstants.PARAM_OKM_NODE_OUTGOING);
		requestDelete = new RequestDelete(getUserInSession().getUsername(),
				getUserInSession().getId(), getIp(), IConstants.SYSTEM_NAME,
				getOkmToken());
		requestDelete.setDirectory(true);
		requestDelete.setPath(okmNodeOutgoing + IConstants.OKM_FOLDER_DELIMITER
				+ ((ProtocolBook) entity).getProtocolSeries()
				+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
				+ ((ProtocolBook) entity).getProtocolYear());
		responseDelete = okmDispatcher.delete(requestDelete);
		if (responseDelete.isError()) {
			log.error(responseDelete.toString());
			throw new RuntimeException(responseDelete.toString());
		}
	}

}
