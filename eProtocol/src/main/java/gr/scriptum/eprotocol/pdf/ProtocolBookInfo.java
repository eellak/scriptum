package gr.scriptum.eprotocol.pdf;

import gr.scriptum.domain.ProtocolBook;

import java.util.Date;

public class ProtocolBookInfo {
	public static final String HEADER = "Βιβλίο Πρωτοκόλλου ";
	public static final String FOOTER = "Βιβλίο Πρωτοκόλλου ";
	
	
	
	ProtocolBook bookInfo;
	String title = "", subject = "" , csvKeywords = "" , author = "" , creator = "", company = "";
	Date startDate, stopDate;
	String file = "c:\\SCRIPTUM_eProtocol.pdf";
	String dateFormat = "dd/MM/yyyy HH:mm";
	
	
	public final String getFile() {
		return file;
	}

	public final void setFile(String file) {
		this.file = file;
	}

	
	
	public final String getDateFormat() {
		return dateFormat;
	}

	public final void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public final String getCompany() {
		return company;
	}

	public final void setCompany(String company) {
		this.company = company;
	}

	public final ProtocolBook getBookInfo() {
		return bookInfo;
	}

	public final void setBookInfo(ProtocolBook bookInfo) {
		this.bookInfo = bookInfo;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getSubject() {
		return subject;
	}

	public final void setSubject(String subject) {
		this.subject = subject;
	}

	public final String getCsvKeywords() {
		return csvKeywords;
	}

	public final void setCsvKeywords(String csvKeywords) {
		this.csvKeywords = csvKeywords;
	}

	public final String getAuthor() {
		return author;
	}

	public final void setAuthor(String author) {
		this.author = author;
	}

	public final String getCreator() {
		return creator;
	}

	public final void setCreator(String creator) {
		this.creator = creator;
	}

	public final Date getStartDate() {
		return startDate;
	}

	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public final Date getStopDate() {
		return stopDate;
	}

	public final void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
}