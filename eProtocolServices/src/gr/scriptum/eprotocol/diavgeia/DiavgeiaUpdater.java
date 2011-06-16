package gr.scriptum.eprotocol.diavgeia;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

/**
 * Interface that must follow all updater implementation that target diavgeia.gov.gr in SCRIPTUM
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public interface DiavgeiaUpdater {
	public void updateFromDiavgeia() throws HttpException, IOException,
	ParserConfigurationException, SAXException;
}
