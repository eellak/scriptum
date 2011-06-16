package gr.scriptum.eprotocol.diavgeia;

import gr.scriptum.dao.ParameterDAO;

/**
 * Diavgeia Updater Configuration Object. 
 * The default Urls point to diavgeia.gov.gr production server 
 * @author Mike Mountrakis mountrakis@uit.gr
 */
public class DiavgeiaUpdaterConfig {

	static int timeout = 60 * 1000; // 1 min
	static String urlEidosApofasis = "http://193.105.109.185/api/types.xml";
	static String urlThematikes    = "http://193.105.109.185/api/tags.xml";


	public DiavgeiaUpdaterConfig(){
		init();
	}
	
	private static synchronized void  init(){
		ParameterDAO parameterDAO = new ParameterDAO();
		urlEidosApofasis = parameterDAO.getAsString( "DIAVGEIA_URL_EIDOS_APOFASIS");
		urlThematikes = parameterDAO.getAsString("DIAVGEIA_URL_EIDOS_THEMATIK");
		timeout = parameterDAO.getAsInteger("DIAVGEIA_TIMEOUT");
	}

	public  String getUrlEidosApofasis() {
		return urlEidosApofasis;
	}

	public  String getUrlThematikes() {
		return urlThematikes;
	}	
	
	
	
}
