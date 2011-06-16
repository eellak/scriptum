package gr.scriptum.eprotocol.diavgeia.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * General class that helps parsing XML documents using SAX parser
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class SAXParserHelper extends DefaultHandler {
	public Locator locator;

	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
		System.out.println("SYS ID: " + locator.getSystemId());
	}

	void syntaxError(String err_msg) throws SAXException {
		String msg = "XML (" + locator.getSystemId() + "). " + err_msg;
		System.err.println("ERROR : " + msg);
		throw new SAXException(msg);
	}

	void syntaxErrorForElement(String element, String attribute)
			throws SAXException {
		String msg = "XML (" + locator.getSystemId()
				+ "). You should specify attribute (" + attribute
				+ ") for element (" + element + ") At line : "
				+ locator.getLineNumber();

		System.err.println("ERROR : " + msg);
		throw new SAXException(msg);
	}

	String getAttributesValue(Attributes attrs, String attr_key) {
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name
				String val = attrs.getValue(i); // Attr value

				if (aName.equals(""))
					aName = attrs.getQName(i);

				if (aName.equals(attr_key)) {
					if (val.equals(""))
						return null;
					else
						return val;
				}
			}
		}
		return null;
	}

	String clear( String s ){
		
		if( s == null )
			return null;
		
		char [] scopy = new char[ s.length() ];
		for(int i=0; i<s.length(); i++ )
			if( s.charAt(i) == '\n' )
				scopy[i] = ' ';
			else
				scopy[i] = s.charAt(i);
		
		String str = new String(scopy);
		return str.trim();
	}	
}
