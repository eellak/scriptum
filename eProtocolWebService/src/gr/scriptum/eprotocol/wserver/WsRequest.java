package gr.scriptum.eprotocol.wserver;
/**
 * The parent of all eProtocol WS Requests
 * @author mike
 *
 */

public abstract class WsRequest{
   Integer  clientId;

   public WsRequest(){}
   
   public WsRequest( Integer clientId ){
	   this.clientId = clientId;
   }
   
   public final Integer getClientId() {
	   return clientId;
   }

   public final void setClientId(Integer clientId) {
	   this.clientId = clientId;
   }
}