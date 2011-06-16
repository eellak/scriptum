package gr.scriptum.eprotocol.diavgeia;


import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import gr.scriptum.domain.DiavgeiaDecisionType;
import gr.scriptum.domain.DiavgeiaSubjectGroup;
import gr.scriptum.eprotocol.diavgeia.parsers.*;

/**
 * Diavgeia Updater Implementation Class.
 * Makes use of the  diavgeia.gov.gr restfull API. makes GET requests, parses the returned XML files and return
 * ArrayList of DiavgeiaDecisionType and DiavgeiaSubjectGroup.
 * The default Urls point to diavgeia.gov.gr production server 
 * @author Mike Mountrakis mountrakis@uit.gr
 */

public class DiavgeiaUpdaterImpl extends HttpDispatcher implements DiavgeiaUpdater{
	static boolean DEBUG = true;

	DiavgeiaUpdaterConfig config;

	HttpClient httpclient = null;

	ArrayList<DiavgeiaDecisionType> eidiApofaseon = new ArrayList<DiavgeiaDecisionType>();
	ArrayList<DiavgeiaSubjectGroup> thematikesEnotites = new ArrayList<DiavgeiaSubjectGroup>();

	public DiavgeiaUpdaterImpl(DiavgeiaUpdaterConfig config) {
		setDebug(DEBUG);
		this.config = config;
		httpclient = new HttpClient();
		httpclient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(DiavgeiaUpdaterConfig.timeout);
	}

	public ArrayList<DiavgeiaDecisionType> getEidiApofaseon() {
		return eidiApofaseon;
	}

	public ArrayList<DiavgeiaSubjectGroup> getThematikesEnotites() {
		return thematikesEnotites;
	}

	public void updateFromDiavgeia() throws HttpException, IOException,
			ParserConfigurationException, SAXException {
		getDecisionTypes();
		getSubjectTypes();
	}

	public static void main(String gargv[]) {
		try {
			DiavgeiaUpdaterConfig config = new DiavgeiaUpdaterConfig();
			DiavgeiaUpdaterImpl updater = new DiavgeiaUpdaterImpl( config );
			updater.updateFromDiavgeia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getDecisionTypes() throws HttpException, IOException,
			ParserConfigurationException, SAXException {

		DiavgeiaParserConfig eidosApofasisCfg = new DiavgeiaParserConfig();
		eidosApofasisCfg.setElementStart("ns3:decisionType");
		eidosApofasisCfg.setAttributeUid("uid");
		eidosApofasisCfg.setElementDescritpion("ns3:label");

		ArrayList<DiavgeiaEntry> parsedEntries = parse( config.getUrlEidosApofasis(), eidosApofasisCfg);

		debug("--------------------------------------");
		for (DiavgeiaEntry entry : parsedEntries) {
			debug(entry.toString());
			DiavgeiaDecisionType apofasi = new  DiavgeiaDecisionType();
			apofasi.setUid(entry.getUid());
			apofasi.setDescription(entry.getDescription());
			eidiApofaseon.add(apofasi);
		}
	}

	private void getSubjectTypes() throws HttpException, IOException,
			ParserConfigurationException, SAXException {

		DiavgeiaParserConfig subjectTypesCfg = new DiavgeiaParserConfig();
		subjectTypesCfg.setElementStart("ns3:tag");
		subjectTypesCfg.setAttributeUid("uid");
		subjectTypesCfg.setElementDescritpion("ns3:label");

		ArrayList<DiavgeiaEntry> parsedEntries = parse( config.getUrlThematikes(), subjectTypesCfg);

		debug("--------------------------------------");
		for (DiavgeiaEntry entry : parsedEntries) {
			debug(entry.toString());
			DiavgeiaSubjectGroup thematiki = new DiavgeiaSubjectGroup();
			thematiki.setUid(entry.getUid());
			thematiki.setDescription(entry.getDescription());
			thematikesEnotites.add(thematiki);
		}
	}

	private ArrayList<DiavgeiaEntry> parse(String url,
			DiavgeiaParserConfig parserConfig) throws HttpException,
			IOException, SAXException, ParserConfigurationException {
		HttpMethod method = new GetMethod(url);
		debugRequest("parse()", method);
		int res = httpclient.executeMethod(method);
		debug("Response : " + res);
		debugResponse("parse()", method);

		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();

		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = sp.getXMLReader();
		/* Create a new ContentHandler and apply it to the XML-Reader */
		DiavgeiaParser decisionTypesParser = new DiavgeiaParser(parserConfig);
		xr.setContentHandler(decisionTypesParser);

		/* Parse the xml-data from our URL. */
		xr.parse(new InputSource(method.getResponseBodyAsStream()));

		debug("--------------------------------------");
		ArrayList<DiavgeiaEntry> parsedEntries = decisionTypesParser
				.getEntries();

		return parsedEntries;
	}

}
