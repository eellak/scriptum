/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class UploadVM extends BaseVM {

	private static Log log = LogFactory.getLog(UploadVM.class);

	public static final String PAGE = "upload.zul";

	private ProtocolDocument protocolDocument = null;

	private List<DocumentType> documentTypes = null;

	private Callback callback = null;

	@Init
	public void init() {
		// setup callback
		callback = (Callback) Executions.getCurrent().getArg().get(IConstants.PARAM_CALLBACK);
		// setup document types, by protocol book
		ProtocolBook protocolBook = (ProtocolBook) Executions.getCurrent().getArg().get(IConstants.PARAM_PROTOCOL_BOOK);
		DocumentTypeDAO documentTypeDAO = SpringUtil.getApplicationContext().getBean(DocumentTypeDAO.class);
		documentTypes = documentTypeDAO.findByProtocolBookAndApplicableType(protocolBook, ApplicableType.DOCUMENT,null,null);

		protocolDocument = new ProtocolDocument();
	}
	
	@Command
	public void scan(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {
		Window uploadWin = (Window) Executions.createComponents("scanning.zul", view, null);
		uploadWin.setClosable(true);
		uploadWin.doModal();
	}


	@Command
	@NotifyChange("protocolDocument")
	public void upload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		Media media = event.getMedia();
		protocolDocument.setDocumentName(media.getName());
		protocolDocument.setDocumentMime(media.getContentType());

		// TODO: handle IO exceptions gracefully
		byte[] content = null;

		try {
			if (media.isBinary()) { // doc, xls, pdf etc
				if (media.inMemory()) {
					content = media.getByteData();
				} else {
					content = IOUtils.toByteArray(media.getStreamData());
				}
			} else { // non binary, e.g text based
				if (media.inMemory()) {
					content = media.getStringData().getBytes();
				} else {
					content = IOUtils.toByteArray(media.getReaderData());
				}
			}
		} catch (IOException e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("error"), Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		}
		protocolDocument.setContent(content);
		protocolDocument.setDocumentSize(new Long(content.length));
	}

	@Command
	@NotifyChange("protocolDocument")
	public void confirm(@ContextParam(ContextType.VIEW) Window uploadWin) {
		validateFields(uploadWin);
		if (protocolDocument.getContent() == null) {
			Messagebox.show(Labels.getLabel("uploadPage.noFileUploaded"), Labels.getLabel("error"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// notify caller (if any)
		if (callback.getCaller() != null) {
			// called by MVC controller
			Events.postEvent(callback.getEvent(), callback.getCaller(), protocolDocument);
		} else {
			// called by MVVM view model
			Map<String, Object> args = new HashMap<>();
			args.put("entity", protocolDocument);
			BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
		}
		uploadWin.detach();
	}

	public ProtocolDocument getProtocolDocument() {
		return protocolDocument;
	}

	public void setProtocolDocument(ProtocolDocument protocolDocument) {
		this.protocolDocument = protocolDocument;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

}
