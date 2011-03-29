package gr.scriptum.eprotocol.ws;


/**
 * The dispacher configuration object provides the endpoint port address.
 * @author mike
 *
 */
public class OkmDispatcherConfig {
	private  String OKMAuthPort_address    = "http://localhost:8080/OpenKM/OKMAuth";
	private String OKMDocumentPort_address = "http://localhost:8080/OpenKM/OKMDocument";
	private String OKMFolderPort_address   = "http://localhost:8080/OpenKM/OKMFolder";
	private String OKMSearchPort_address   = "http://localhost:8080/OpenKM/OKMSearch";	
	
	public String getOKMAuthPort_address() {
		return OKMAuthPort_address;
	}
	public void setOKMAuthPort_address(String oKMAuthPort_address) {
		OKMAuthPort_address = oKMAuthPort_address;
	}
	public String getOKMDocumentPort_address() {
		return OKMDocumentPort_address;
	}
	public void setOKMDocumentPort_address(String oKMDocumentPort_address) {
		OKMDocumentPort_address = oKMDocumentPort_address;
	}
	public String getOKMFolderPort_address() {
		return OKMFolderPort_address;
	}
	public void setOKMFolderPort_address(String oKMFolderPort_address) {
		OKMFolderPort_address = oKMFolderPort_address;
	}
	public String getOKMSearchPort_address() {
		return OKMSearchPort_address;
	}
	public void setOKMSearchPort_address(String oKMSearchPort_address) {
		OKMSearchPort_address = oKMSearchPort_address;
	}



}
