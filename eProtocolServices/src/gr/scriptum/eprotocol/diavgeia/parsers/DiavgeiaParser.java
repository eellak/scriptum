package gr.scriptum.eprotocol.diavgeia.parsers;


import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;



public class DiavgeiaParser extends SAXParserHelper {

	DiavgeiaParserConfig config;
	String currentElement = "";
	DiavgeiaEntry currentEntry;
	ArrayList<DiavgeiaEntry> parsedEntries = new ArrayList<DiavgeiaEntry>();

	
	public DiavgeiaParser( DiavgeiaParserConfig config ){
		this.config = config;
	}
	
	public void startElement(String namespaceURI, String lName, String qName,
			Attributes attrs) throws SAXException {
		String eName = lName; // element name

		if (eName.equals(""))
			eName = qName; // namespaceAware = false
		currentElement = eName;
		//System.out.println(eName);
		if (eName.equals(config.getElementStart())) {
			currentEntry = new DiavgeiaEntry();

			String uid = getAttributesValue(attrs, config.getAttributeUid());
			currentEntry.setUid(uid);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String value = new String(ch, start, length);
		value = clear(value);
		//System.out.println("VALUE[" + value + "]");
		if (!value.equals("")) {
			if (currentElement.equalsIgnoreCase(config.getElementDescritpion())) {
				currentEntry.setDescription(value);
			}
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String eName = localName; // element name

		if (eName.equals(""))
			eName = qName; // namespaceAware = false

		//System.out.println(eName);
		if (eName.equals(config.getElementStart())) {
			parsedEntries.add(currentEntry);
		}
	}

	public ArrayList<DiavgeiaEntry> getEntries() {
		return parsedEntries;
	}

}
