package gr.scriptum.eprotocol.diavgeia;

/**
 * Diavgeia Dispatcher Configuration Object. 
 * Makes PDF document posts to diavgeia.gov.gr
 * The default baseUrl points to diavgeia.gov.gr test server.
 * 
 *  Remember to use keytool to register the truststore or imply the trustore during java runtime:<br>
 *  java -Djavax.net.ssl.trustStore=truststore -Djavax.net.ssl.trustStorePassword=123456 MyADiavgeiaDispatcherImplpp

 *  
 * @author Mike Mountrakis mountrakis@uit.gr
 */




import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.ProtocolDocument;



public class DiavgeiaDispatcherImpl extends HttpDispatcher implements DiavgeiaDispatcher{
	public static Log log = LogFactory.getLog(DiavgeiaDispatcherImpl.class);

	static boolean DEBUG = true;
	
	DiavgeiaDispatcherConfig config = null;
	HttpClient httpclient = null;
	
	
	
	public DiavgeiaDispatcherImpl(){
		setDebug(DEBUG);
		this.config = DiavgeiaDispatcherConfig.getInstance();
		registerKeyStores();
		httpclient = new HttpClient();
		httpclient.getParams().setHttpElementCharset("UTF-8");
	    httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(config.getTimeout());
	}
	
/**
 * Sends to DIAVGEIA
 * In case of failed login, timouw while reaching DIAVGEIA, fail to ge session id, it throws an exception
 * In case of failing sending a PDF it sets the error description and ADA code is null
 */
	
	@Override
	public List<DiavgeiaReceipt> sendOutgoingProtocol( OutgoingProtocol outProtocol, 
			                                           DiavgeiaReferenceData diavgeiaData ) throws Exception{
		
		ArrayList<DiavgeiaReceipt> receipts = new ArrayList<DiavgeiaReceipt>();
		
		try {
	        
			String sessionId = getSessionId();
			if( sessionId == null )
				throw new Exception("Null session id. Could not Connect.");
			else
				debug("Session id is [" + sessionId + "]");
			
			// Get initial state object and add a cookie with the session id
			createCookie(sessionId);
             
			String redirected = doLogin();
			if(  redirected == null )
			   throw new Exception("Failed to login!");
			else
				debug("Redirected to " + redirected);
			
			Set<ProtocolDocument> docsList =  outProtocol.getProtocolDocuments();
			for( ProtocolDocument doc : docsList){
				if( isEligibleFile( doc.getDocumentName() )  ){
					log.debug("Processing document: "+doc.getId());
					
					DiavgeiaReceipt receipt = new DiavgeiaReceipt(sessionId, doc.getId());
					DiavgeiaRequest theRequest = null;
					try{
						theRequest = prepareRequest( outProtocol, doc, diavgeiaData );
	
						String confirmationId = postProtocolFile(theRequest);
						if(  confirmationId == null )
						   throw new Exception("Failed to store the pdf!");
						else
							debug("PFD Stored!");
									
						redirected = postConfirmationId(confirmationId);
						if(  redirected == null )
							throw new Exception("Failed to post Confirmation for id " + confirmationId);
						else
							debug("Redirected to " + redirected);			
					
						String adaCode =  getADA( redirected );
						if(  adaCode != null )
							debug("ADA CODE for submission is [" + adaCode + "]");			
										
				
						receipt.confirmationId = confirmationId;
						receipt.adaCode = adaCode;
					
						debug("Finished without errors");
					}catch(Exception e ){
						receipt.errDescr =  e.getMessage();
					}finally{
						if( theRequest != null ){
							if(theRequest.file != null){
								if( theRequest.file.exists() )
									theRequest.file.delete();
							}
						}
					}
					receipts.add(receipt);
				}
			}			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return receipts;

	}	
	
	


	public static void main(String[] argv) {
	}

	
	/**
	 * Initializes the TrustStore to be used for SSL configuration
	 */
	private void registerKeyStores(){
		System.setProperty("javax.net.ssl.trustStore", config.getTrustStoreFile());
		System.setProperty("javax.net.ssl.trustStorePassword", config.getTrustStorePass());		
	}  
	
	
	/**
	 * Returns the session ID passed from server
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
   private String getSessionId() throws HttpException, IOException{
		String sessionId = null;
		
		HttpMethod method = new GetMethod( config.getBaseUrl() + "/login.php");
		debugRequest( "getSessionId()",  method );
        int res =  httpclient.executeMethod(method);
        debugResponse( "getSessionId()",  method );
        
        if( res != 200 )
        	return sessionId;
		
        return searchHeadersFor("Set-Cookie", method);
	}	
	
	
	
	/**
	 * Creates a cookie with the sessionId so server can track us. This cookie is planted in httpclient in the initial state.
	 * @param httpclient the httpclient to create the cookie
	 * @param sessionId the given session id to be included in the cookie
	 * @return the httpclient with the planted cookie
	 * @throws ParseException
	 */
	private void createCookie( String sessionId ) throws ParseException{
		// Get initial state object and add a cookie with the session id
        HttpState initialState = new HttpState();		
		Cookie cook = new Cookie();
		cook.setName("PHPSESSID");
		cook.setValue(sessionId);
		cook.setDomain(config.getBaseUrl());
		cook.setPath("/");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date expireDate = df.parse("31/12/2050");           
		cook.setExpiryDate(expireDate);
		initialState.addCookie(cook);			
		httpclient.setState(initialState);	
	}
		
	
	
	/**
	 * Performs the login using a post request
	 * @param httpclient
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private String  doLogin() throws HttpException, IOException{
	 
		HttpMethod method = new PostMethod(config.getBaseUrl() + "/login.php");
		NameValuePair restore_session = new NameValuePair("restore_session", URIUtil.encodeQuery("1"));
		NameValuePair edUser = new NameValuePair("edUser", URIUtil.encodeQuery(config.getUser()));
		NameValuePair edUser_hidden = new NameValuePair("edUser_hidden", URIUtil.encodeQuery(config.getUser()));
		NameValuePair edPassword = new NameValuePair("edPassword", URIUtil.encodeQuery(config.getPassword()));
		NameValuePair edPassword_hidden = new NameValuePair("edPassword_hidden", URIUtil.encodeQuery(config.getPassword()));
		NameValuePair btnLoginSubmitEvent = new NameValuePair("btnLoginSubmitEvent", URIUtil.encodeQuery("btnLogin_btnLoginClick"));
		NameValuePair captcha_code = new NameValuePair("captcha_code", URIUtil.encodeQuery(""));
		NameValuePair btnLogin = new NameValuePair("btnLogin", URIUtil.encodeQuery("1"));
		NameValuePair[] params = new NameValuePair[] {restore_session, edUser, edUser_hidden, edPassword, 
				                                      edPassword_hidden,  btnLoginSubmitEvent, captcha_code,btnLogin};
		method.setQueryString(params);

	    debugRequest( "doLogin()",  method );
		httpclient.executeMethod(method);
		debugResponse( "doLogin()",  method );
			
        return searchHeadersFor("Location", method);
 }
	
	
	
	private DiavgeiaRequest prepareRequest( OutgoingProtocol outProtocol, 
			                                ProtocolDocument doc, 
			                                DiavgeiaReferenceData diavgeiaData ) throws IOException{
		
		DiavgeiaRequest theRequest = new DiavgeiaRequest();
		theRequest.arithmosProtokolou = "" + outProtocol.getProtocolNumber();
		theRequest.thema = outProtocol.getSubject();
		theRequest.setApofasiDate(outProtocol.getProtocolDate());
		
		byte [] contents = doc.getContent();
		File pdfFile = new File( config.getTempFilePath() + 
				                 File.separator +  
				                 doc.getDocumentName());
		FileOutputStream out = new FileOutputStream(pdfFile);
		for (int i = 0; i < doc.getContent().length; i++) {
			out.write( contents[i] );
		}
		out.flush();
		out.close();
		theRequest.file = pdfFile;
	
		
		//from the DiavgeiaEidosApofasis
		theRequest.eidosApofasis =  diavgeiaData.getApofasiUid();
		theRequest.thematikiEnotitaValuesText = diavgeiaData.getThematikiUid();
			
		return theRequest;
		
	}
	
	
	
	/**
	 * Posts a simple PDF to diavgia. Upon successful submission, it returns the confirmation id.
	 * @param httpclient
	 * @return
	 * @throws Exception
	 */
	String postProtocolFile(DiavgeiaRequest theRequest ) throws Exception{
		String id =null;
		PostMethod  method = new PostMethod( config.getBaseUrl() + "/apofaseis.php");
		
		Part[] parts = {
			new StringPart("newForm", URIUtil.encodeQuery("1")),
			new StringPart("field_arithmos_protokolou", URIUtil.encodeQuery(theRequest.arithmosProtokolou)),
			new StringPart("field_apofasi_date", URIUtil.encodeQuery(theRequest.apofasiDate)),
			new StringPart("field_syntaktis_email", URIUtil.encodeQuery(config.getEmail())),
			new StringPart("field_level2_text", URIUtil.encodeQuery(config.getOrganizationId())),
			new StringPart("field_thema", URIUtil.encodeQuery(theRequest.thema)),    //diorismos 
			new StringPart("field_eidos_apofasis", URIUtil.encodeQuery(theRequest.eidosApofasis)),   //diorismos
			new StringPart("field_monades_text", URIUtil.encodeQuery(config.getUnitId())),
			new StringPart("field_telikos_ypografwn", URIUtil.encodeQuery(config.getSigns())),
			new StringPart("field_thematiki_enotita_values_text", URIUtil.encodeQuery(theRequest.thematikiEnotitaValuesText)),
			new FilePart("field_file", theRequest.file.getName(), theRequest.file)
		};
		
        method.setRequestEntity( new MultipartRequestEntity(parts, method.getParams()) );

	    debugRequest( "postProtocolFile()",  method );
		httpclient.executeMethod(method);
		debugResponse( "postProtocolFile()",  method );

		//check if we are redirected 
		String redirect = searchHeadersFor("Location", method);
		if( redirect == null )
			debug("Not Redirected...");
		else{	
			debug("Redirtected to " + redirect);
			if( redirect.contains("/confirmation.php?id=" )){
				id = redirect.substring( redirect.lastIndexOf('=') + 1 );
				debug("ID for registration confirmation is = " + id );
				
			}
		}
		
		return id;
        
	}
	
	
	
	/**
	 * Makes the PDF's permanent registration using the confirmationId
	 * @param httpclient
	 * @param confirmationId
	 * @return
	 * @throws Exception
	 */
	private String postConfirmationId(  String confirmationId) throws Exception{
		HttpMethod method = new PostMethod(config.getBaseUrl() + "/confirmation.php");
		NameValuePair form_action = new NameValuePair("form_action", URIUtil.encodeQuery("finalsubmit"));
		NameValuePair id = new NameValuePair("id", URIUtil.encodeQuery(confirmationId));
		NameValuePair[] params = new NameValuePair[] {form_action, id};
		method.setQueryString(params);

	    debugRequest( "postConfirmationId(" + confirmationId +")",  method );
		httpclient.executeMethod(method);
		debugResponse( "postConfirmationId()",  method );
			
		//check if we are redirected 
		String redirect = searchHeadersFor("Location", method);
		if( redirect == null )
			debug("Not Redirected...");
		else
			debug("Redirected to " + redirect);	
		
		return redirect;
	}
	



		
	/**
	 * After the confirmation of document registration, the http client is redirected to a new page
	 * This page contains the ADA. This ADA is to be parsed from this method.
	 * @param httpclient
	 * @param adaPageLink
	 * @return
	 * @throws Exception
	 */
	private String getADA(String adaPageLink ) throws Exception{
		String adaCode = null;
		
		HttpMethod method = new GetMethod(adaPageLink);
		debugRequest( "getADA()",  method );
        int res =  httpclient.executeMethod(method);
        debugResponse( "getADA()",  method );
        
        if( res != 200 )
        	return adaCode;
        
        
        
		//<div id="LabelADA" style=" font-family: Verdana; font-size: 12px; text-align: center; height:21px;width:603px;"   ><b>ΑΔΑ: </b>4ΑΘΣΕΣ-Κ</div>
        adaCode = searchResponseStreamFor( method.getResponseBodyAsStream(), 
        		                           "LabelADA", "</b>", "</div>" );
        
        return adaCode;
	}
	
	
		


	
  //Minor Helpers  
 /**
  * Searches the response headers for a given Name and parse the value
  * @param headerName that we search for. Valid are Set-Cookie, Location
  * @param method the response
  * @return
  */
   private String searchHeadersFor(String headerName,  HttpMethod method){
    	String needle = null;
    	Header headers[] = method.getResponseHeaders();
		for(int i=0; i< headers.length; i++){
			if( headers[i].getName().startsWith(headerName)){
				if( headerName.equals("Set-Cookie")){
					String val = headers[i].getValue();
					needle = val.substring( val.indexOf('=')+1, val.indexOf(';'));
					break;
				}else if( headerName.equals("Location") ){
					String val = headers[i].getValue();
					needle = val.substring( val.indexOf("https"));
					break;					
				}
			}
		}
		
		return needle;   	
    }
    	
	
   
   private String searchResponseStreamFor( InputStream is, 
		                                   String mustContain, 
		                                   String startToken, 
		                                   String stopToken ) throws IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line, value = null;
		while ((line = rd.readLine()) != null) {
			if( line.contains(mustContain ) ){
				int startIdx = line.indexOf(startToken) + startToken.length();
				int stopIdx = -1;
				if( stopToken != null )
					stopIdx = line.indexOf(stopToken);
				
				if( stopIdx != -1 )
					value = line.substring(startIdx,stopIdx);
				else
					value = line.substring(startIdx);
			}
		}		
		//rd.close();
		System.out.println("Found [" + value + "]");
		
		return value;
	}    
   
   
	private boolean isEligibleFile( String filename ){
		
		int idx = filename.lastIndexOf('.');
		if( idx == -1 )
			return false;
			
		
		String suffix = filename.substring( idx );
		if( ".pdf".equalsIgnoreCase(suffix) )
			return true;
		else
			return false;	
	}	
	
}
 