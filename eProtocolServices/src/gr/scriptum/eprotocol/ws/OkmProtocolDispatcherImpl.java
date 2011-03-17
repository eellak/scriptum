package gr.scriptum.eprotocol.ws;



import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ScriptumDocument;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import com.openkm.ws.endpoint.Document;
import com.openkm.ws.endpoint.Folder;
import com.openkm.ws.endpoint.OKMAuthProxy;
import com.openkm.ws.endpoint.OKMDocumentProxy;
import com.openkm.ws.endpoint.OKMFolderProxy;
import com.openkm.ws.endpoint.OKMSearchProxy;
import com.openkm.ws.endpoint.QueryResult;
import com.openkm.ws.endpoint.RepositoryException;


/**
* TODO
* 1) create proxy references in a method init() that will be called as a singleton call.
* so that we save small time from proxy reconstruction/re-bind.
* 
* 2) Fix the problem from OpenKM that there are no messages to thrown exceptions when a folder or a file
* is forced to recreated (already exists). In this case, the exceptions thrown contain null error message.
* 
* 3) Ask Paco to provide a small explanation about how his Search criteria in ws method OKMSearch.find() 
* are used.
*/


/**
 * @author mike
 * @version 1.0 
 * Implementation of Scriptum to OpenKM interface over AXIS2 libraries
 * Web Service dispatcher class provides the implementation of
 *          interface between eProtocol application with OpenKM.
 */
public final class OkmProtocolDispatcherImpl  implements OkmProtocolDispatcher{
	

	
	private enum ProxyType{
		AUTH,
		DOCUMENT,
		FOLDER,
		SEARCH
	}
	
	private static final char OKM_SEP = '/';
	
	private static Logger logger = Logger.getLogger(OkmProtocolDispatcherImpl.class.getName());

	private OkmDispatcherConfig configuration;
	
	//------------
	// Constructors
	//------------
	public OkmProtocolDispatcherImpl(){
		
	}
	
	public OkmProtocolDispatcherImpl( OkmDispatcherConfig configuration ){
		this.configuration = configuration;	
	}
		
	
	//---------------
	//
	//---------------
	private java.rmi.Remote getInstance(ProxyType pType) throws Exception {

		switch (pType) {
		case AUTH:
			OKMAuthProxy authProxy = new OKMAuthProxy();
			if (configuration != null) {
				authProxy.setEndpoint(configuration.getOKMAuthPort_address());
				logger.warning("Binding with "
						+ configuration.getOKMAuthPort_address());
			}
			return authProxy;
		case DOCUMENT:
			OKMDocumentProxy documentProxy = new OKMDocumentProxy();
			if (configuration != null) {
				documentProxy.setEndpoint(configuration
						.getOKMDocumentPort_address());
				logger.info("Binding with "
						+ configuration.getOKMDocumentPort_address());
			}
			return documentProxy;
		case FOLDER:
			OKMFolderProxy folderProxy = new OKMFolderProxy();
			if (configuration != null) {
				folderProxy.setEndpoint(configuration
						.getOKMFolderPort_address());
				logger.warning("Binding with "
						+ configuration.getOKMFolderPort_address());
			}
			return folderProxy;

		case SEARCH:
			OKMSearchProxy searchProxy = new OKMSearchProxy();
			if (configuration != null) {
				searchProxy.setEndpoint(configuration
						.getOKMSearchPort_address());
				logger.warning("Binding with "
						+ configuration.getOKMSearchPort_address());
			}
		default:
			throw new Exception("You must specify the WS proxy type!");
		}
	}
	
	
	
	
	//------------
	// Interface implementation methods
	//------------	
   public ResponseLogin login( RequestLogin loginRequest ){
	   ResponseLogin loginResponse = new ResponseLogin(loginRequest);
	   
	   try{
		   OKMAuthProxy authProxy = (OKMAuthProxy)getInstance(ProxyType.AUTH);
		   String okmToken = authProxy.login(loginRequest.getUsername(),loginRequest.getPassword() );
		   loginResponse.setOkmToken(okmToken);
	   }catch(Throwable e){
		   loginResponse = (ResponseLogin)createErrorResponse(loginResponse, 
				                                              e,
				                                              DISPACTER_ERROR.ECODE_LOGIN);
	   }
	   return loginResponse;
   }
	
   
   public Response logout( RequestLogout logoutRequest ){
	   Response logoutResponse = new Response(logoutRequest);
	   
	   try{
		   OKMAuthProxy authProxy = (OKMAuthProxy)getInstance(ProxyType.AUTH);
		   authProxy.logout( logoutRequest.getOkmToken());
	   }catch(Throwable  e){
		   logoutResponse = createErrorResponse(logoutResponse,e,DISPACTER_ERROR.ECODE_LGOUT);
	   }
	   return logoutResponse;
   }	
   
   
   public Response delete(RequestDelete deleteRequest ){
      Response deleteResponse = new Response(deleteRequest);
	  String eCode = DISPACTER_ERROR.ECODE_NO_ERROR; 
	  
	   try{
		  if( deleteRequest.isDirectory() ){
			  eCode = DISPACTER_ERROR.ECODE_DEL_ND;
			  OKMFolderProxy folderProxy = (OKMFolderProxy)getInstance(ProxyType.FOLDER);
			  folderProxy.delete(deleteRequest.getOkmToken(), deleteRequest.getPath());
		  }else{
			  eCode = DISPACTER_ERROR.ECODE_DEL_FI;
			  OKMDocumentProxy documentProxy = (OKMDocumentProxy)getInstance(ProxyType.DOCUMENT);
			  documentProxy.delete(deleteRequest.getOkmToken(), deleteRequest.getPath());
		  }
	   }catch(Throwable  e){
		   deleteResponse = (Response)createErrorResponse(deleteResponse,e,eCode);
	   }
	   
	   return deleteResponse;	   
   }
   
   
   
   public ResponseAddDocumentToNode  addDocumentToNode( RequestAddDocumentToNode  newDocRequest ){
	   ResponseAddDocumentToNode newDocResponse = new ResponseAddDocumentToNode(newDocRequest);
		try{
			OKMFolderProxy   folderProxy   = (OKMFolderProxy)  getInstance(ProxyType.FOLDER);	
			OKMDocumentProxy documentProxy = (OKMDocumentProxy)getInstance(ProxyType.DOCUMENT);	
			
			Folder nodeFolder =  folderProxy.getProperties( newDocRequest.getOkmToken(),
					                                    newDocRequest.getNodePath() );
			if( nodeFolder != null ){
				Document openKmDoc = openKmDocumentFromScriptumDoc( nodeFolder, 
						                                            newDocRequest.getDocument());
				openKmDoc = documentProxy.create(newDocRequest.getOkmToken(),
                          openKmDoc, 
                          newDocRequest.getDocument().getContent());
				
				debugDocument(openKmDoc);
				//we do not need the binary content no more since it has been stored to repository....
				ScriptumDocument scriptumDocument = newDocRequest.getDocument();
				
				scriptumDocument = scriptumDocumentFromOpenKmDocument(scriptumDocument,openKmDoc);

				//we do not need the binary content no more since it has been stored to repository....
				scriptumDocument.setContent(null);
				
				newDocResponse.setDocument(scriptumDocument);				
			}
		}catch(Throwable  e ){
			newDocResponse = (ResponseAddDocumentToNode) createErrorResponse( newDocResponse,  e, DISPACTER_ERROR.ECODE_ADD_FI);
		}
		
   	return newDocResponse;	   
   }
   
    

   public ResponseNewNode createNewNode( RequestNewNode newNodeRequest ){
		ResponseNewNode newNodeResponse = new ResponseNewNode(newNodeRequest);
		
		try{
			OKMFolderProxy folderProxy = (OKMFolderProxy)getInstance(ProxyType.FOLDER);		
			Folder nodeFolder = folderProxy.createSimple( newNodeRequest.getOkmToken(), 
					                                      newNodeRequest.getFolderPath() );
			newNodeResponse.setDocumentFolder(nodeFolder);
			
			OKMDocumentProxy documentProxy = (OKMDocumentProxy)getInstance(ProxyType.DOCUMENT);	
			ArrayList<ScriptumDocument> documents = newNodeRequest.getDocumentList();
			for (Iterator<ScriptumDocument> i = documents.iterator(); i.hasNext(); ){
				ScriptumDocument scriptumDocument = i.next();
				Document openKmDoc = openKmDocumentFromScriptumDoc( nodeFolder, scriptumDocument);						
				openKmDoc =  documentProxy.create(newNodeRequest.getOkmToken(),
	  		                                      openKmDoc, 
						                          scriptumDocument.getContent());
				
				
				scriptumDocument = scriptumDocumentFromOpenKmDocument(scriptumDocument,openKmDoc);
                
				//we do not need the binary content no more since it has been stored to repository....
				scriptumDocument.setContent(null);
			
				debugDocument( openKmDoc );
				
				newNodeResponse.addDocument(scriptumDocument);
			}
		
		}catch(Throwable  e ){
			newNodeResponse = (ResponseNewNode) createErrorResponse( newNodeResponse,  e, DISPACTER_ERROR.ECODE_NEW_ND);
		}
		
   	return newNodeResponse;
   }
   
   
   /**
    * Function to create a virtual folder with documents on the OpenKM repository.
    * @param protocolRequest  the protocol request
    * @return a ResponseNewNode containing the OpenKm paths/uuid of the files you placed. 
    */
   public ResponseNewNode createProtocolNode( RequestProtocolNode protocolRequest ){	
		
			String    nodePath= createDocumentPath( protocolRequest.getProtocolBook().getProtocolPath(),
					                               "" + protocolRequest.getProtocolNode().getProtocolNumber().intValue() );
						
			RequestNewNode newProtocolNode = new RequestNewNode(protocolRequest);
			newProtocolNode.setFolderPath(nodePath);
			
			Set<ProtocolDocument> documents = protocolRequest.getProtocolNode().getProtocolDocuments();
			for (Iterator<ProtocolDocument> i = documents.iterator(); i.hasNext(); )
				newProtocolNode.addDocument(i.next());
			
			return createNewNode( newProtocolNode );
   }
   
  
   /**
    * Rename a node
    * @param renameRequest specifies the oldName and newName along with session authentication.
    * @return
    */
	public ResponseRenameNode renameNode( RequestRenameNode renameRequest ){
		ResponseRenameNode resp = new ResponseRenameNode( renameRequest );
		
		try{
			
			OKMFolderProxy folderProxy = (OKMFolderProxy)getInstance(ProxyType.FOLDER);			
			Folder nodeFolder = folderProxy.rename( renameRequest.getOkmToken(), 
					                                renameRequest.getOldName(), 
					                                renameRequest.getNewName());	
            resp.setDocumentFolder(nodeFolder); 
			logger.info("Renamed : " + nodeFolder.toString() );
		}catch(RepositoryException e){
			resp =  (ResponseRenameNode)createErrorResponse( resp, e, DISPACTER_ERROR.ECODE_REN_ND);
		}catch(Exception e ){
			e.printStackTrace();
		}
		return resp;
	}
	
	

	public Response moveNode( RequestMoveNode moveRequest ){
		Response resp = new Response( moveRequest );
		
		try{
			
			OKMFolderProxy folderProxy = (OKMFolderProxy)getInstance(ProxyType.FOLDER);		
			folderProxy.move( moveRequest.getOkmToken(),
					          moveRequest.getOldPath(), 
					          moveRequest.getNewPath());	
       
			logger.info("Node Renamed : " + moveRequest.getOldPath() + " to " + moveRequest.getNewPath());
		}catch(RepositoryException e){
			resp =  createErrorResponse( resp, e, DISPACTER_ERROR.ECODE_MOV_ND);
		}catch(Exception e ){
			e.printStackTrace();
		}
		return resp;
	}	
	
	
	
	

	
	
	
	/**
	 * We only need search by content, since all other search ways are implemented in eProtocol
	 * using the metadata.
	 * @param req
	 * @return
	 */
	public ResponseSearch searchByContent( RequestSearch  req){
		ResponseSearch resp = new ResponseSearch( req );
		
		try{
			OKMSearchProxy searchProxy = (OKMSearchProxy)getInstance(ProxyType.SEARCH);
			
			QueryResult[] results = searchProxy.findByContent( req.getOkmToken(), req.getContent());
			if( results != null){
				for(int i=0; i<results.length; i++){
					QueryResult result = results[i];
					if( result!= null ){
						if( result.getDocument() != null){
							ScriptumDocument doc = new ScriptumDocument();
							doc = scriptumDocumentFromOpenKmDocument( new ScriptumDocument(), 
									                                  result.getDocument());
							resp.addDocument(doc);
						}
					}
				}
			}
			logger.info("Found : " + resp.getSearchResults().size() );
		}catch(Exception e){ 
			resp = (ResponseSearch) createErrorResponse( resp, e, DISPACTER_ERROR.ECODE_SEARC);
		}
		return resp;		
	}
	
	
	public ResponseSendDocument sendDocument(RequestSendDocument req){
		Document openKmDoc  = null;
		byte [] content = null;
		ResponseSendDocument  resp = new ResponseSendDocument(req);
		
		
		try{	
			OKMDocumentProxy documentProxy = (OKMDocumentProxy)getInstance(ProxyType.DOCUMENT);	
			if( documentProxy.isValid(req.getOkmToken(), req.getDocumentPath()) ){
				openKmDoc  = documentProxy.getProperties(req.getOkmToken(), req.getDocumentPath());
				content = documentProxy.getContent(req.getOkmToken(), req.getDocumentPath(), true);
				
				resp.setDocumentInfo(openKmDoc);
				resp.setContent(content);
			}else 
				throw new Exception("Path is not valid! ("+ req.getDocumentPath() +")");
		
		}catch(Throwable  e ){
			resp = (ResponseSendDocument) createErrorResponse( resp,  e, DISPACTER_ERROR.ECODE_REQ_FI);
		}	
		
		return resp;
	}
	
	
   
   //----------
   // Helpers.
   //-----------

	/**
	 * The function to create an OpenKM document from a Scriptum Document
	 * 
	 * @param targetFolder a reference to the node's folder where this document will be created
	 * @param scriptumDoc the Scriptum Document
	 * @return a new OpenKM document.
	 */   
	public Document openKmDocumentFromScriptumDoc(Folder targetFolder,
			                                       ScriptumDocument scriptumDoc) {
		
		Document openKmDoc = new Document();
		
		String filePath = createDocumentPath( targetFolder.getPath(), 
				                              scriptumDoc.getDocumentName());
		openKmDoc.setPath(filePath);
		openKmDoc.setKeywords( scriptumDoc.getKeywords() );
		return openKmDoc;
    }

	
    /**
     * Function to merge Scriptum and OpenKM file information to a Scriptum file.
     * @param scriptumDoc the original Scriptum file
     * @param okmDoc when the scriptumDoc was sent to OKM reppository, it was 
     * qualified with a uuid and a path. Those are held in okmDoc.  
     * @return the merged information to the ScriptumDocument.
     */
	public ScriptumDocument scriptumDocumentFromOpenKmDocument( ScriptumDocument scriptumDoc, 
			                                                    Document okmDoc){
		scriptumDoc.setOkmPath( okmDoc.getPath());
		scriptumDoc.setOkmUuid(okmDoc.getUuid());
		if( okmDoc.getActualVersion() != null)
			scriptumDoc.setDocumentSize( okmDoc.getActualVersion().getSize() );
		return scriptumDoc;
	}
	
	
	/**
	 * From a folder and a file name derive the file path in OpenKm
	 * @param targetFolder the folder
	 * @param scriptumDoc  the document
	 * @return the path, something like <br>
	 * <i>/okm:root/IncomingProtocol/2011/10026/myFile.txt</i>
	 */
	private static final String createDocumentPath( String targetFolder, String fileName  ){
		String filePath = "";

		if (targetFolder.charAt(targetFolder.length() - 1) == OKM_SEP) {
			filePath = targetFolder + fileName;
		} else
			filePath = targetFolder + OKM_SEP + fileName;

		return filePath;		
	}
	
	
	@SuppressWarnings("unused")  //for the future.
	private static final String getFilenameFromPath( String path ){
		
		if( path.charAt( path.length() - 1 ) == OKM_SEP )
			return null; //path is Folder
		
		return path.substring( path.lastIndexOf(OKM_SEP) + 1 );		
	}
	
	
	
	/**
	 * Standard error response method called from all w/s methods
	 * @param resp the response object   
	 * @param request  the initial request caused for this response
	 * @param e  the error exception or whatever caused trouble
	 * @param eCode the error code
	 * @return a Response Object that has enough information for the caller to use.
	 */
	private static Response createErrorResponse( Response resp, 
												 Throwable e, 
												 String eCode){
		
		if( e instanceof RepositoryException){
			RepositoryException rex = (RepositoryException)e;
			resp.seteMessage(rex.getMessage1());
		}else
			resp.seteMessage(e.getMessage());
		
		logger.severe("Exception : " +resp.geteMessage() );
		resp.seteCode(eCode);
		resp.setCompletedNow();
		return resp;
	}

	
	
	private static void debugDocument( Document openKmDoc ){
		logger.info("Document Path   : " + openKmDoc.getPath() );
		logger.info("Document UUID   : " + openKmDoc.getUuid() );
		logger.info("Document Author : " + openKmDoc.getAuthor() );
		if( openKmDoc.getActualVersion() != null ){
			logger.info("Document Created: " + openKmDoc.getActualVersion().getCreated() );
			logger.info("Document Size   : " + openKmDoc.getActualVersion().getSize() );
			logger.info("Document Comment: " + openKmDoc.getActualVersion().getComment() );	
		}
	}
	
	
	

   /**
    * Test drive.
    * @param argv
    */
	
	public static void main(String argv []){
		
		try{
			OkmDispatcherConfig       config  = new OkmDispatcherConfig();
			OkmProtocolDispatcherImpl service = new OkmProtocolDispatcherImpl(config);
			
			RequestLogin loginReq = new RequestLogin();
			loginReq.setUsername("mountrakis");
			loginReq.setPassword("qweasd");
		
	
		    ResponseLogin respLogin = service.login( loginReq );
	
			if( respLogin.isError() ){
				System.err.println( "FATAL  ---------->   " + respLogin.toString() );
				return;
			}
			System.out.println("logged in : " + respLogin.getOkmToken());
			
			/*
			RequestNewNode newNodeRequest  = new RequestNewNode(loginReq);
			newNodeRequest.setOkmToken(respLogin.getOkmToken());
			newNodeRequest.setFolderPath("/okm:root/IncomingProtocol/TEST");
			
			byte [] content = "Kalhmera OpenKM eimai o Mixalis.".getBytes();
			
			ProtocolDocument doc = new ProtocolDocument();
			doc.setContent(content);
			doc.setDocumentName("Test1.txt");
			doc.setDocumentSize( new Long(content.length) );
			doc.setDocumentKeywords("Kalhmera,OpenKM");
			doc.setDocumentNumber(new Integer(1));
		
			ArrayList<ScriptumDocument> docList = new ArrayList<ScriptumDocument> ();
			docList.add(doc);
			newNodeRequest.setDocumentList(docList);
			
		    ResponseNewNode newNodeResponse = service.createNewNode(newNodeRequest);
			if( newNodeResponse.isError() ){
				System.err.println( "FATAL  ---------->   " + newNodeResponse.toString() );
				return;
			}
			System.out.println("Document created. Debugging: ");
			ArrayList<ScriptumDocument> docs = newNodeResponse.getDocumentList();
			for( ScriptumDocument d : docs){
				d.toString();
			}
		    */
			
			/*
			RequestRenameNode renameRequest = new RequestRenameNode();
			renameRequest.setOkmToken(respLogin.getOkmToken());
			renameRequest.setOldName("/okm:root/PendingProtocol/5");
			renameRequest.setNewName("/okm:root/IncomingProtocol/2011/dolhpin");		
			ResponseRenameNode resp =  service.renameNode(renameRequest );
			*/
			
			RequestMoveNode moveRequest = new RequestMoveNode();
			moveRequest.setOkmToken(respLogin.getOkmToken());
			moveRequest.setOldPath("/okm:root/PendingProtocol/5");
			moveRequest.setNewPath("/okm:root/IncomingProtocol/2011");
						
			Response resp =  service.moveNode(moveRequest );
			System.out.println(resp.toString());
			
			service.logout( new RequestLogout(respLogin) );
	
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	
}