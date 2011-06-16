package gr.scriptum.eprotocol.wserver;
/**
 * Dispatcher Interface for  eProtocol Web Service Client.
 * Directs how clients to eProtocol Web Service should operate
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public interface EProtocolWebService {
	public ProtocolReceipt receiveProtocol( ProtocolMessage message);
	public ProtocolInfo inquireProtocol( ProtocolQuery query );
}
