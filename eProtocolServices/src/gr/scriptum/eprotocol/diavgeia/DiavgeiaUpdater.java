package gr.scriptum.eprotocol.diavgeia;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

public interface DiavgeiaUpdater {
	public void updateFromDiavgeia() throws HttpException, IOException,
	ParserConfigurationException, SAXException;
}
