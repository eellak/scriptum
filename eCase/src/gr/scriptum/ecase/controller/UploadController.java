/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.TaskDocument;
import gr.scriptum.ecase.util.IConstants;
import gr.scriptum.util.Callback;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * @author aanagnostopoulos
 * 
 */
public class UploadController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 655726668632327749L;

	private static Log log = LogFactory.getLog(UploadController.class);

	public static final String PAGE = "upload.zul";

	/* data binding */
	private TaskDocument taskDocument = null;

	private List<DocumentType> documentTypes = null;

	private Callback callback = null;

	/* components */
	Window uploadWin;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		callback = (Callback) arg.get(IConstants.PARAM_CALLBACK); // setup
		// callback

		DocumentTypeDAO documentTypeDAO = new DocumentTypeDAO();
		documentTypes = documentTypeDAO.findAll();

		taskDocument = new TaskDocument();
		// taskDocument.setDocumentNumber(incomingController
		// .getProtocolDocuments().size() + 1);

	}

	public void onUpload$uploadBtn(UploadEvent event) throws IOException {
		Media media = event.getMedia();
		taskDocument.setDocumentName(media.getName());
		taskDocument.setDocumentMime(media.getContentType());

		// TODO: handle IO exceptions gracefully
		byte[] content = null;

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
		taskDocument.setContent(content);
		taskDocument.setDocumentSize(new Long(content.length));

		getBinder(uploadWin).loadAll();

	}

	public void onClick$confirmBtn() throws InterruptedException {

		validateFields(uploadWin);
		if (taskDocument.getContent() == null) {
			Messagebox.show(
					Labels.getLabel("taskPage.upload.noFileUploaded"),
					Labels.getLabel("error"), Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (callback != null) { // notify caller
			Events.postEvent(callback.getEvent(), callback.getCaller(),
					taskDocument);
		}
		// incomingController.getProtocolDocuments().add(taskDocument);
		// incomingController.getBinder(incomingController.incomingWin).loadAll();
		uploadWin.onClose();

	}

	public TaskDocument getTaskDocument() {
		return taskDocument;
	}

	public void setTaskDocument(TaskDocument protocolDocument) {
		this.taskDocument = protocolDocument;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

}
