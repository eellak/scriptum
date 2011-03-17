package gr.scriptum.domain;

import java.util.Arrays;

/**
 * The document with byte content that will be stored to the repository (OpenKM)
 * 
 * @author mike
 * 
 */
public class ScriptumDocument {

	public static final String DILIMITER = ",";

	private Integer id;
	private byte[] content;
	private Long documentSize = 0L;
	private String documentMime = "";
	private String documentName = "";
	private Integer documentNumber = 0;
	private String documentKeywords = "";
	private DocumentType documentType;

	// -------------------------------
	// After document is stored in openKm
	// -------------------------------
	private String okmPath;
	private String okmUuid;

	public ScriptumDocument() {
	}

	public ScriptumDocument(DocumentType documentType) {
		this.documentType = documentType;
	}

	public ScriptumDocument(Integer id, byte[] content, Long documentSize,
			String documentMime, String documentName, Integer documentNumber,
			String documentKeywords, DocumentType documentType) {
		this.id = id;
		this.content = content;
		this.documentSize = documentSize;
		this.documentMime = documentMime;
		this.documentName = documentName;
		this.documentNumber = documentNumber;
		this.documentKeywords = documentKeywords;
		this.documentType = documentType;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDocumentSize() {
		return documentSize;
	}

	public void setDocumentSize(Long documentSize) {
		this.documentSize = documentSize;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public Integer getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Integer documentNumber) {
		this.documentNumber = documentNumber;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getDocumentKeywords() {
		return documentKeywords;
	}

	public void setDocumentKeywords(String documentKeywords) {
		this.documentKeywords = documentKeywords;
	}

	public String[] getKeywords() {
		if (documentKeywords != null) {
			if (!documentKeywords.equals("")) {
				return documentKeywords.split(DILIMITER);
			}
		}
		return null;
	}

	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getOkmPath() {
		return okmPath;
	}

	public void setOkmPath(String okmPath) {
		this.okmPath = okmPath;
	}

	public String getOkmUuid() {
		return okmUuid;
	}

	public void setOkmUuid(String okmUuid) {
		this.okmUuid = okmUuid;
	}

	@Override
	public String toString() {
		return "ScriptumDocument [id=" + id + ", content="
				+ Arrays.toString(content) + ", documentSize=" + documentSize
				+ ", documentName=" + documentName + ", documentNumber="
				+ documentNumber + ", documentKeywords=" + documentKeywords
				+ ", documentType=" + documentType + ", okmPath=" + okmPath
				+ ", okmUuid=" + okmUuid + "]";
	}

	public String getDocumentMime() {
		return documentMime;
	}

	public void setDocumentMime(String documentMime) {
		this.documentMime = documentMime;
	}

}
