/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gr.scriptum.dao.IncomingProtocolDAO;
import gr.scriptum.dao.OutgoingProtocolDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.ProtocolNode;
import gr.scriptum.eprotocol.pdf.EProtocolPdfPrinter;
import gr.scriptum.eprotocol.pdf.ProtocolBookInfo;
import gr.scriptum.eprotocol.util.IConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProtocolBookController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8943605066567858168L;

	private static Log log = LogFactory.getLog(ProtocolBookController.class);

	/* data binding */
	private Date from = null;

	private Date to = null;

	private String bookInfoCompany = null;

	private String bookInfoAuthor = null;

	private String bookInfoCreator = null;

	private String bookInfoKeywords = null;

	private String bookInfoSubject = null;

	private String bookInfoTitle = null;

	private String bookInfoFile = null;

	private String fontFolder = null;

	/* components */
	Window win;
	Combobox sortCbx;
	Combobox sortOrderCbx;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		page.setAttribute(this.getClass().getSimpleName(), this);

		ParameterDAO parameterDAO = new ParameterDAO();
		bookInfoCompany = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_COMPANY);
		bookInfoAuthor = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_AUTHOR);
		bookInfoCreator = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_CREATOR);
		bookInfoKeywords = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_KEYWORDS);
		bookInfoSubject = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_SUBJECT);
		bookInfoTitle = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_TITLE);
		bookInfoFile = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_FILE);
		fontFolder = parameterDAO
				.getAsString(IConstants.PARAM_PROTOCOL_BOOK_FONT_FOLDER);

		sortCbx.setSelectedIndex(0);
		sortOrderCbx.setSelectedIndex(0);

	}

	public void onClick$exportBtn(Event event) throws InterruptedException {

		validateFields(win);

		String[] tokens = sortCbx.getSelectedItem().getValue().toString()
				.split(IConstants.SORTING_DELIMITER);
		List<Order> sortBy = new LinkedList<Order>();
		for (String token : tokens) {
			Order order = null;
			String sortOrder = sortOrderCbx.getSelectedItem().getValue()
					.toString();
			if (sortOrder.equals("ascending")) {
				order = Order.asc(token);
			} else {
				order = Order.desc(token);
			}
			sortBy.add(order);
		}

		List<ProtocolNode> protocols = new LinkedList<ProtocolNode>();

		IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
		List<IncomingProtocol> results = incomingProtocolDAO.search(null, from,
				to, null, null, null, null, false, null, null,
				sortBy.toArray(new Order[0]));

		protocols.addAll(results);

		OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
		List<OutgoingProtocol> outgoingResults = outgoingProtocolDAO.search(
				null, from, to, null, null, null, null, false, null, null,
				sortBy.toArray(new Order[0]));

		protocols.addAll(outgoingResults);

		ProtocolBookInfo bookInfo = new ProtocolBookInfo();
		bookInfo.setStartDate(from);
		bookInfo.setStopDate(to);
		bookInfo.setCompany(bookInfoCompany);
		bookInfo.setAuthor(bookInfoAuthor);
		bookInfo.setCreator(bookInfoCreator);
		bookInfo.setCsvKeywords(bookInfoKeywords);
		bookInfo.setSubject(bookInfoSubject);
		bookInfo.setTitle(bookInfoTitle);
		bookInfo.setFile(bookInfoFile);

		EProtocolPdfPrinter printer = new EProtocolPdfPrinter(bookInfo,
				protocols, fontFolder);
		try {
			File file = printer.produceProtocolBook();
			Filedownload.save(file, "application/pdf");

			Messagebox.show(Labels.getLabel("protocolBookPage.exportSuccess"),
					Labels.getLabel("success.title"), Messagebox.OK,
					Messagebox.INFORMATION);

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("protocolBookPage.exportError"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;

		}

	}

	public void onClick$clearBtn(Event event) {
		from = null;
		to = null;

		getBinder(win).loadAll();

		sortCbx.setSelectedIndex(0);
		sortOrderCbx.setSelectedIndex(0);

	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

}
