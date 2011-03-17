package gr.scriptum.eprotocol.wsclient;

public class ProtocolWebServiceClientConfig {
	String portAddress = "http://127.0.0.1:8080/eProtocolTestWebService/ProtocolWebService";

	public final String getPortAddress() {
		return portAddress;
	}

	public final void setPortAddress(String portAddress) {
		this.portAddress = portAddress;
	}
	
}
