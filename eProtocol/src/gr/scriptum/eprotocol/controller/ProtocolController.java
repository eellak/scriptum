/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestSendDocument;
import gr.scriptum.eprotocol.ws.ResponseSendDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

/**
 * @author aanagnostopoulos
 * 
 */
public abstract class ProtocolController extends BaseController {

	private static Log log = LogFactory.getLog(ProtocolController.class);

	protected ProtocolDocument protocolDocument = null;

	protected ResponseSendDocument fetchDocumentFromOpenKM(
			ProtocolDocument protocolDocument) {

		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();
		RequestSendDocument requestSendDocument = new RequestSendDocument(
				getUserInSession().getUsername(), getUserInSession().getId(),
				getIp(), IConstants.SYSTEM_NAME, getOkmToken());
		requestSendDocument.setDocumentPath(protocolDocument.getOkmPath());
		ResponseSendDocument responseSendDocument = okmDispatcher
				.sendDocument(requestSendDocument);
		if (responseSendDocument.isError()) {
			log.error(responseSendDocument.toString());
			throw new RuntimeException(responseSendDocument.toString());
		}

		return responseSendDocument;
	}

	public void onClick$downloadFileBtn() throws InterruptedException {

		try {

			if (protocolDocument.getId() == null) { // not stored in OpenKM,
													// available locally
				Filedownload.save(protocolDocument.getContent(),
						protocolDocument.getDocumentMime(),
						protocolDocument.getDocumentName());
			} else { // document stored in OpenKM, retrieve content

				ResponseSendDocument responseSendDocument = fetchDocumentFromOpenKM(protocolDocument);

				Filedownload.save(responseSendDocument.getContent(),
						responseSendDocument.getMimeType(),
						protocolDocument.getDocumentName());
			}

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("error"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public boolean isDownloadFileBtnDisabled() {

		if (protocolDocument == null) {
			return true;
		}

		return false;

	}

	public ProtocolDocument getProtocolDocument() {
		return protocolDocument;
	}

	public void setProtocolDocument(ProtocolDocument protocolDocument) {
		this.protocolDocument = protocolDocument;
	}

}
