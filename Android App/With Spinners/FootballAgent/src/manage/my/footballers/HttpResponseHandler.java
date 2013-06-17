// Ronan Reilly 2012package manage.my.footballers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HttpResponseHandler extends DefaultHandler{
	private String status;
	private boolean inStatus;
	
	public void startDocument() throws SAXException{
		this.status = null;
		this.inStatus = false;
	}
	
	public void endDocument() throws SAXException{		
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (localName.equals("status")) this.inStatus = true;
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("status")) this.inStatus = false;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (this.inStatus) this.status = new String(ch, start, length);
	}

	public String getStatus() {
		return status;
	}
	
	
}
