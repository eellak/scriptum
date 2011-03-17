package gr.scriptum.eprotocol.ws;

public interface OkmProtocolDispatcher {
	ResponseLogin login(RequestLogin loginRequest);

	Response logout(RequestLogout logoutRequest);

	Response delete(RequestDelete deleteRequest);

	ResponseAddDocumentToNode addDocumentToNode(RequestAddDocumentToNode newDocRequest);

	ResponseNewNode createNewNode(RequestNewNode newNodeRequest);

	ResponseNewNode createProtocolNode(RequestProtocolNode protocolRequest);

	ResponseRenameNode renameNode(RequestRenameNode renameRequest);
	
	Response moveNode( RequestMoveNode moveRequest );

	ResponseSearch searchByContent(RequestSearch searchRequest);
	
	ResponseSendDocument sendDocument(RequestSendDocument req);
}
