package gr.scriptum.eprotocol.wserver;

public interface EProtocolWebService {
	public ProtocolReceipt receiveProtocol( ProtocolMessage message);
	public ProtocolInfo inquireProtocol( ProtocolQuery query );
}
