package gr.scriptum.eprotocol.pdf;

import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.OutgoingRecipient;
import gr.scriptum.domain.ProtocolNode;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

//see page 60  kedy.pdf

public class EProtocolPdfPrinter extends AProtocolPrinter {

	private String fontFolder;

	BaseFont bfGreek = null;
	Font fontGreek9 = null, fontGreek12 = null, fontGreek20 = null;
	PdfPTable inTable = null, outTable = null;

	Document pdfDocument = null;

	// public EProtocolPdfPrinter(ProtocolBookInfo bookInfo) {
	// super(bookInfo);
	// }

	private void initializeFonts() throws Exception {

		// File f = new File(".");
		// System.out.println("The path is " + f.getAbsolutePath());

		// bfGreek = BaseFont.createFont(fontFolder+"/ARIALBD.TTF", "Cp1253",
		// BaseFont.NOT_EMBEDDED);
		bfGreek = BaseFont.createFont(fontFolder + "/TIMESBD.TTF",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		fontGreek9 = new Font(bfGreek, 9, Font.UNDEFINED, Color.BLACK);
		fontGreek12 = new Font(bfGreek, 12, Font.UNDEFINED, Color.BLACK);
		fontGreek20 = new Font(bfGreek, 20, Font.UNDEFINED, Color.BLACK);
	}

	private void createBookAddMetadata() throws FileNotFoundException,
			DocumentException {
		pdfDocument = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
		PdfWriter.getInstance(pdfDocument, new FileOutputStream(bookInfo.file));

		pdfDocument.addTitle(bookInfo.getTitle());
		pdfDocument.addSubject(bookInfo.getSubject());
		pdfDocument.addKeywords(bookInfo.getCsvKeywords());
		pdfDocument.addAuthor(bookInfo.getAuthor());
		pdfDocument.addCreator(bookInfo.getCreator());
	}

	private void initializeTables() {
		// Incoming table
		inTable = new PdfPTable(6);
		inTable.setComplete(true);
		inTable.setWidthPercentage(100);

		Phrase p = new Phrase("Αρ. Πρωτοκόλλου", fontGreek9);
		PdfPCell ci1 = new PdfPCell(p);

		p = new Phrase("Ημ.Πρωτοκόλλησης", fontGreek9);
		PdfPCell ci2 = new PdfPCell(p);

		p = new Phrase("Θέμα", fontGreek9);
		PdfPCell ci3 = new PdfPCell(p);

		p = new Phrase("Αποστολέας", fontGreek9);
		PdfPCell ci4 = new PdfPCell(p);

		p = new Phrase("Τόπος", fontGreek9);
		PdfPCell ci5 = new PdfPCell(p);

		// p = new Phrase("Χρεώθηκε σε", fontGreek9);
		// PdfPCell ci6 = new PdfPCell(p);

		p = new Phrase("Σχόλια", fontGreek9);
		PdfPCell ci6 = new PdfPCell(p);

		inTable.addCell(ci1);
		inTable.addCell(ci2);
		inTable.addCell(ci3);
		inTable.addCell(ci4);
		inTable.addCell(ci5);
		inTable.addCell(ci6);
		inTable.setHeaderRows(1);

		// Creating the Outgoing table
		outTable = new PdfPTable(6);
		outTable.setWidthPercentage(100);
		outTable.setComplete(true);

		p = new Phrase("Αρ. Πρωτοκόλλου", fontGreek9);
		PdfPCell co1 = new PdfPCell(p);

		p = new Phrase("Ημ. Πρωτοκόλλησης", fontGreek9);
		PdfPCell co2 = new PdfPCell(p);

		p = new Phrase("Θέμα", fontGreek9);
		PdfPCell co3 = new PdfPCell(p);

		p = new Phrase("Παραλήπτης", fontGreek9);
		PdfPCell co4 = new PdfPCell(p);

		p = new Phrase("Ημ. Διεκπαιραίωσης", fontGreek9);
		PdfPCell co5 = new PdfPCell(p);

		// p = new Phrase("Σχετικό", fontGreek9);
		// PdfPCell co6 = new PdfPCell(p);

		p = new Phrase("Σχόλια", fontGreek9);
		PdfPCell co7 = new PdfPCell(p);

		outTable.addCell(co1);
		outTable.addCell(co2);
		outTable.addCell(co3);
		outTable.addCell(co4);
		outTable.addCell(co5);
		// outTable.addCell(co6);
		outTable.addCell(co7);
		outTable.setHeaderRows(1);
	}

	// Na to dw me ton Aggelo...
	private void populateTables() {
		for (ProtocolNode node : bookData) {
			System.out.println(node.toString());

			if (node.getDirection() == ProtocolNode.Direction.INCOMING) {
				IncomingProtocol inProto = (IncomingProtocol) node;
				// incomingProtocolToTableRow( inProto );

				String s1 = inProto.getProtocolNumber().toString();
				String s2 = formatDate(inProto.getProtocolDate());
				String s3 = inProto.getSubject();
				String s4 = inProto.getContact().getFullName() + " ("
						+ inProto.getContact().getCompany().getName() + ")";
				String s5 = inProto.getIncomingPlace();
				String s6 = inProto.getComments();

				inTable.addCell(new Phrase(s1, fontGreek9));
				inTable.addCell(new Phrase(s2, fontGreek9));
				inTable.addCell(new Phrase(s3, fontGreek9));
				inTable.addCell(new Phrase(s4, fontGreek9));
				inTable.addCell(new Phrase(s5, fontGreek9));
				inTable.addCell(new Phrase(s6, fontGreek9));
			} else {
				OutgoingProtocol outProto = (OutgoingProtocol) node;
				// outgoingProtocolToTableRow( outProto );
				String s1 = outProto.getProtocolNumber().toString();
				String s2 = formatDate(outProto.getProtocolDate());
				String s3 = outProto.getSubject();

				StringBuffer sb = new StringBuffer();
				for (OutgoingRecipient outgoingRecipient : outProto
						.getOutgoingRecipients()) {
					sb.append(outgoingRecipient.getId().getContact()
							.getFullName()
							+ " ("
							+ outgoingRecipient.getId().getContact()
									.getCompany().getName() + ")" + ",");
				}
				String s4 = sb.substring(0, sb.length() - 1);
				String s5 = formatDate(outProto.getOutgoingDate());
				// String s6 = outProto.getRelativeProtocol();
				String s7 = outProto.getComments();

				outTable.addCell(new Phrase(s1, fontGreek9));
				outTable.addCell(new Phrase(s2, fontGreek9));
				outTable.addCell(new Phrase(s3, fontGreek9));
				outTable.addCell(new Phrase(s4, fontGreek9));
				outTable.addCell(new Phrase(s5, fontGreek9));
				// outTable.addCell(new Phrase(s6, fontGreek9));
				outTable.addCell(new Phrase(s7, fontGreek9));
			}
		}

	}

	private void incomingProtocolToTableRow(IncomingProtocol inProto) {

		String contactDetails = getContact(inProto);

		/*
		 * String distribution = getDistributionMethod(inProto);
		 * 
		 * String attInfo = ""; ArrayList<String> attachements =
		 * getAttachements( inProto ); for(String attachement : attachements )
		 * attInfo += attachement + ",";
		 */

		String s1 = "" + inProto.getProtocolNumber();
		String s2 = formatDate(inProto.getProtocolDate());
		String s3 = inProto.getSubject();
		String s4 = inProto.getIncomingPlace();
		String s5 = contactDetails;
		String s6 = getUserCreated(inProto);

		inTable.addCell(new Phrase(s1, fontGreek9));
		inTable.addCell(new Phrase(s2, fontGreek9));
		inTable.addCell(new Phrase(s3, fontGreek9));
		inTable.addCell(new Phrase(s4, fontGreek9));
		inTable.addCell(new Phrase(s5, fontGreek9));
		inTable.addCell(new Phrase(s6, fontGreek9));
	}

	private void outgoingProtocolToTableRow(OutgoingProtocol outProto) {
		ArrayList<String> receipients = getReceipients(outProto);
		String contactDetails = "";
		for (String receipient : receipients)
			contactDetails += receipient + ",";

		/*
		 * String distribution = getDistributionMethod( outProto );
		 * 
		 * String attInfo = ""; ArrayList<String> attachements =
		 * getAttachements( outProto ); for(String attachement : attachements )
		 * attInfo += attachement + ",";
		 */
		String s1 = contactDetails;
		String s2 = outProto.getSubject();
		String s3 = formatDate(outProto.getProtocolDate());
		String s4 = formatDate(outProto.getOutgoingDate());
		String s5 = outProto.getRelativeProtocol();
		String s6 = outProto.getComments();

		outTable.addCell(new Phrase(s1, fontGreek9));
		outTable.addCell(new Phrase(s2, fontGreek9));
		outTable.addCell(new Phrase(s3, fontGreek9));
		outTable.addCell(new Phrase(s4, fontGreek9));
		outTable.addCell(new Phrase(s5, fontGreek9));
		outTable.addCell(new Phrase(s6, fontGreek9));
	}

	private void createTestData(int n) {
		bookData = new ArrayList<ProtocolNode>();
		for (int i = 0; i < n; i++) {
			long time = 1234499532345L + 100000 * i;

			Date d = new Date(time);
			IncomingProtocol ip = new IncomingProtocol();
			ip.setProtocolNumber(new Integer(i));
			ip.setProtocolDate(d);
			ip.setSubject("Αλλη μια χαρούμενη μέρα...");
			ip.setComments("Σχόλια");
			ip.setIncomingPlace("Χαλάνδρι");
			bookData.add(ip);

			Date d1 = new Date(time + 2000);
			OutgoingProtocol op = new OutgoingProtocol();
			op.setProtocolNumber(new Integer(i));
			op.setProtocolDate(d1);
			op.setOutgoingDate(d1);
			op.setSubject("Αλλη μια χαρούμενη μέρα...");
			op.setComments("Να λείπουν τα Σχόλια για το report μου...");
			bookData.add(op);
		}
	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public EProtocolPdfPrinter(ProtocolBookInfo bookInfo,
			List<ProtocolNode> bookData, String fontFolder) {
		super(bookInfo);
		this.bookData = bookData;
		this.fontFolder = fontFolder;
	}

	@Override
	public File produceProtocolBook() throws Exception {

		initializeFonts();

		createBookAddMetadata();

		initializeTables();

		// used in test only
		// createTestData(100);

		populateTables();

		String strIncoming = "Εισερχόμενο Πρωτόκολλο  ";
		String strOutgoing = "Εξερχόμενο Πρωτόκολλο  ";

		// Headers and footers
		HeaderFooter proHeader = new HeaderFooter(new Phrase(
				ProtocolBookInfo.HEADER, fontGreek9), true);
		proHeader.setAlignment(Element.ALIGN_CENTER);

		HeaderFooter proFooter = new HeaderFooter(new Phrase(
				ProtocolBookInfo.FOOTER, fontGreek9), true);
		proFooter.setAlignment(Element.ALIGN_CENTER);

		HeaderFooter inHeader = new HeaderFooter(new Phrase(strIncoming,
				fontGreek9), true);
		inHeader.setAlignment(Element.ALIGN_CENTER);

		HeaderFooter inFooter = new HeaderFooter(new Phrase(strIncoming,
				fontGreek9), true);
		inFooter.setAlignment(Element.ALIGN_CENTER);

		HeaderFooter outHeader = new HeaderFooter(new Phrase(strOutgoing,
				fontGreek9), true);
		outHeader.setAlignment(Element.ALIGN_CENTER);

		HeaderFooter outFooter = new HeaderFooter(new Phrase(strOutgoing,
				fontGreek9), true);
		outFooter.setAlignment(Element.ALIGN_CENTER);

		// create the chapters
		Chapter prologueCh = new Chapter(0);
		Paragraph pTitle = new Paragraph(bookInfo.getTitle(), fontGreek20);
		pTitle.setAlignment(Element.ALIGN_CENTER);
		Paragraph pPrologue = new Paragraph(
				"Βιβλίο πρωτοκόλλου εγγράφων της υπηρεσίας "
						+ bookInfo.getCompany() + "\nΓια την περίοδο:\n"
						+ formatDate(bookInfo.getStartDate()) + " - "
						+ formatDate(bookInfo.getStopDate())
						+ "\nΗμερομηνία παραγωγής εγγράφου: "
						+ formatDate(new Date()) + "\nΑπό τον χρήστη: "
						+ bookInfo.getCreator() + "\nΑρχείο παραγωγής: "
						+ bookInfo.getFile(), fontGreek12);
		prologueCh.add(pTitle);
		addEmptyLine(pTitle, 5);
		prologueCh.add(pPrologue);
		prologueCh.newPage();

		Chapter incomingCh = new Chapter(1);
		Paragraph pIncoming = new Paragraph(strIncoming, fontGreek20);
		pIncoming.setAlignment(Element.ALIGN_CENTER);
		incomingCh.add(pIncoming);
		incomingCh.newPage();
		incomingCh.add(inTable);

		Chapter outgoingCh = new Chapter(2);
		Paragraph pOutgoing = new Paragraph(strOutgoing, fontGreek20);
		pOutgoing.setAlignment(Element.ALIGN_CENTER);
		outgoingCh.add(pOutgoing);
		outgoingCh.newPage();
		outgoingCh.add(outTable);

		// Compile the document
		pdfDocument.open();
		pdfDocument.setHeader(proHeader);
		pdfDocument.setFooter(proFooter);
		pdfDocument.add(prologueCh);

		pdfDocument.setHeader(inHeader);
		pdfDocument.setFooter(inFooter);
		pdfDocument.add(incomingCh);

		pdfDocument.setHeader(outHeader);
		pdfDocument.setFooter(outFooter);
		pdfDocument.add(outgoingCh);

		pdfDocument.close();

		return new File(bookInfo.file);

	}

	public static void main(String[] argv) {

		ProtocolBookInfo bookInfo = new ProtocolBookInfo();
		bookInfo.setStartDate(new Date());
		bookInfo.setStopDate(new Date());
		bookInfo.setCompany("UIT Unified Infrmation Technology");
		bookInfo.setAuthor("Mike Mountrakis");
		bookInfo.setCreator("Mike Mountrakis");
		bookInfo.setCsvKeywords("Βιβλίο Πρωτοκόλλου");
		bookInfo.setSubject("Βιβλίο Πρωτοκόλλου");
		bookInfo.setTitle("Βιβλίο Πρωτοκόλλου");
		bookInfo.setFile("c:\\SCRIPTUM_eProtocol.pdf");

		EProtocolPdfPrinter printer = new EProtocolPdfPrinter(bookInfo, null,
				"");
		printer.createTestData(100);

		try {

			File f = new File(".");
			System.out.println("The path is " + f.getAbsolutePath());

			printer.produceProtocolBook();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getFontFolder() {
		return fontFolder;
	}

	public void setFontFolder(String fontFolder) {
		this.fontFolder = fontFolder;
	}

}
