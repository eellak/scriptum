package gr.scriptum.eprotocol.wsclient;
import gr.scriptum.eprotocol.wserver.ProtocolInfo;
import gr.scriptum.eprotocol.wserver.ProtocolMessage;
import gr.scriptum.eprotocol.wserver.ProtocolQuery;
import gr.scriptum.eprotocol.wserver.ProtocolReceipt;


public interface EProtocolWebServiceClient {
	 public ProtocolReceipt sendProtocol (ProtocolMessage message ) throws Exception;
	 public ProtocolInfo  inquireProtocol(ProtocolQuery query) throws Exception;
}
