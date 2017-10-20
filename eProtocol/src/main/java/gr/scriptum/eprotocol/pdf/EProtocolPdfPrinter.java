package gr.scriptum.eprotocol.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolNode;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.eprotocol.util.TextUtils;

//see page 60  kedy.pdf

public class EProtocolPdfPrinter extends AProtocolPrinter {

	private static Log log = LogFactory.getLog(EProtocolPdfPrinter.class);

	BaseFont bfGreek = null;
	Font fontGreek9 = null, fontGreek12 = null, fontGreek20 = null;
	Font fontGreek9Red = null;
	PdfPTable inTable = null, outTable = null;

	Document pdfDocument = null;

	private void initializeFonts() throws Exception {

		URL resource = this.getClass().getClassLoader().getResource("TIMESBD.TTF");
		log.info("font resource:" + resource.getFile());
		bfGreek = BaseFont.createFont(resource.getFile(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		fontGreek9 = new Font(bfGreek, 9, Font.UNDEFINED, Color.BLACK);
		fontGreek9Red = new Font(bfGreek, 9, Font.BOLD, Color.RED);
		fontGreek12 = new Font(bfGreek, 12, Font.UNDEFINED, Color.BLACK);
		fontGreek20 = new Font(bfGreek, 20, Font.UNDEFINED, Color.BLACK);
	}

	private void createBookAddMetadata() throws FileNotFoundException, DocumentException {
		pdfDocument = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
		File file = new File(bookInfo.file);
		log.info("Report file path:"+file.getAbsolutePath());
		PdfWriter.getInstance(pdfDocument, new FileOutputStream(file));

		pdfDocument.addTitle(bookInfo.getTitle());
		pdfDocument.addSubject(bookInfo.getSubject());
		pdfDocument.addKeywords(bookInfo.getCsvKeywords());
		pdfDocument.addAuthor(bookInfo.getAuthor());
		pdfDocument.addCreator(bookInfo.getCreator());
	}

	private void initializeTables() {
		// Incoming table
		inTable = new PdfPTable(8);
		inTable.setComplete(true);
		inTable.setWidthPercentage(100);
		try {
			inTable.setWidths(new int[]{5,5,7,10,30,10,10,22});
		} catch (DocumentException e) {
			log.error(e);
		}

		Phrase p = new Phrase("Αρ. Πρωτ.", fontGreek9);
		PdfPCell ci1 = new PdfPCell(p);

		p = new Phrase("Έτος", fontGreek9);
		PdfPCell yearCell = new PdfPCell(p);

		p = new Phrase("Τύπος", fontGreek9);
		PdfPCell directionCell = new PdfPCell(p);

		p = new Phrase("Ημ. Πρωτ/σης", fontGreek9);
		PdfPCell ci2 = new PdfPCell(p);

		p = new Phrase("Θέμα", fontGreek9);
		PdfPCell ci3 = new PdfPCell(p);

		p = new Phrase("Αποστολέας", fontGreek9);
		PdfPCell ci4 = new PdfPCell(p);

		p = new Phrase("Είδος Εγγράφου", fontGreek9);
		PdfPCell ci5 = new PdfPCell(p);

		p = new Phrase("Παραλήπτες", fontGreek9);
		PdfPCell ci6 = new PdfPCell(p);

		inTable.addCell(ci1);
		inTable.addCell(yearCell);
		inTable.addCell(directionCell);
		inTable.addCell(ci2);
		inTable.addCell(ci3);
		inTable.addCell(ci4);
		inTable.addCell(ci5);
		inTable.addCell(ci6);
		inTable.setHeaderRows(1);
	}

	private void populateTables() {
		for (ProtocolNode node : bookData) {
			log.debug(node.toString());

			Protocol inProto = (Protocol) node;

			String s1 = inProto.getProtocolNumber().toString() + (inProto.getIsDeleted()!=null && inProto.getIsDeleted() ? "\nΑΚΥΡΟ" :"");
			String year = inProto.getProtocolYear().toString();
			String direction = inProto.getDirection().equals(Direction.INCOMING) ? "Εισερχόμενο" : "Εξερχόμενο";
			String s2 = formatDate(inProto.getProtocolDate());
			String s3 = inProto.getSubject();
			String s4 = inProto.getSender() == null ? "" : TextUtils.getLabel(inProto.getSender());
			String s5 = inProto.getDocumentType().getName();
			String s6 = null;
			switch (inProto.getDirection()) {
			case INCOMING:
				s6 = inProto.getInternalRecipients().stream().map(ir -> TextUtils.getLabel(ir)).collect(Collectors.joining(","));
				break;
			case OUTGOING:
				s6 = inProto.getRecipients().stream().map(ir -> TextUtils.getLabel(ir)).collect(Collectors.joining(","));
				break;
			default:
				break;
			}

			inTable.addCell(inProto.getIsDeleted() != null && inProto.getIsDeleted() ? new Phrase(s1, fontGreek9Red) : new Phrase(s1, fontGreek9));
			inTable.addCell(new Phrase(year, fontGreek9));
			inTable.addCell(new Phrase(direction, fontGreek9));
			inTable.addCell(new Phrase(s2, fontGreek9));
			inTable.addCell(new Phrase(s3, fontGreek9));
			inTable.addCell(new Phrase(s4, fontGreek9));
			inTable.addCell(new Phrase(s5, fontGreek9));
			inTable.addCell(new Phrase(s6, fontGreek9));
		}

	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public EProtocolPdfPrinter(ProtocolBookInfo bookInfo, List<ProtocolNode> bookData) {
		super(bookInfo);
		this.bookData = bookData;
	}

	@Override
	public File produceProtocolBook() throws Exception {

		initializeFonts();

		createBookAddMetadata();

		initializeTables();

		populateTables();

		String strIncoming = "Πρωτόκολλημένα Έγγραφα  ";

		// Headers and footers
		HeaderFooter inHeader = new HeaderFooter(new Phrase(strIncoming, fontGreek9), false);
		inHeader.setAlignment(Element.ALIGN_CENTER);

		HeaderFooter inFooter = new HeaderFooter(new Phrase("Σελίδα ", fontGreek9), true);
		inFooter.setAlignment(Element.ALIGN_CENTER);

		// create the chapters
		Chapter prologueCh = new Chapter(0);
		Paragraph pTitle = new Paragraph(bookInfo.getTitle(), fontGreek20);
		pTitle.setAlignment(Element.ALIGN_CENTER);
		Paragraph pPrologue = new Paragraph(bookInfo.getCompany() + "\nΠερίοδος: "
				+ formatDate(bookInfo.getStartDate()) + " - " + formatDate(bookInfo.getStopDate())
				+ "\nΗμερομηνία παραγωγής εγγράφου: " + formatDate(new Date()) + "\nΧρήστης: " + bookInfo.getCreator()
				+ "\nΑρχείο παραγωγής: " + bookInfo.getFile(), fontGreek12);
		prologueCh.add(pTitle);
		addEmptyLine(pTitle, 5);
		prologueCh.add(pPrologue);
		prologueCh.newPage();

		Chapter incomingCh = new Chapter(1);

		incomingCh.add(inTable);

		// Compile the document
		pdfDocument.open();
		pdfDocument.setHeader(inHeader);
		pdfDocument.setFooter(inFooter);
		pdfDocument.add(prologueCh);

		pdfDocument.setHeader(inHeader);
		pdfDocument.setFooter(inFooter);
		pdfDocument.add(incomingCh);

		pdfDocument.close();

		File file = new File(bookInfo.file);
		log.info("Report file path:"+file.getAbsolutePath());
		return file;
	}

}
