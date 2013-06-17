// Ronan Reilly 2012
package manage.my.footballers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyXMLHandler extends DefaultHandler {
	
	private PlayersContainer myPlayers;
	private Player currentPlayer;
	private boolean inTeam;
	private boolean inFirstName;
	private boolean inLastName;
	private boolean inAge;
	private boolean inCountry_Origin;
	private boolean inPosition;
	private boolean inPref_Foot;

	public void startDocument() throws SAXException {
		this.myPlayers = new PlayersContainer();
		this.currentPlayer = null;
		this.inFirstName = false;
		this.inLastName = false;
		this.inAge = false;
		this.inCountry_Origin = false;
		this.inPosition = false;
		this.inPref_Foot = false;
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (localName.equalsIgnoreCase("players")) ;
		else if (localName.equalsIgnoreCase("player")) {
			currentPlayer = new Player();
			currentPlayer.id = Integer.parseInt(atts.getValue("id"));
		}
		else if (localName.equalsIgnoreCase("firstName")) inFirstName = true;
		else if (localName.equalsIgnoreCase("lastName")) inLastName = true;
		else if (localName.equalsIgnoreCase("team")) inTeam = true;
		else if (localName.equalsIgnoreCase("age")) inAge = true;
		else if (localName.equalsIgnoreCase("country_origin")) inCountry_Origin = true;
		else if (localName.equalsIgnoreCase("position")) inPosition = true;
		else if (localName.equalsIgnoreCase("pref_foot")) inPref_Foot = true;
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("players")) ;
		else if (localName.equalsIgnoreCase("player")) {
			myPlayers.addPlayer(currentPlayer);
			currentPlayer = null;
		}
		else if (localName.equalsIgnoreCase("firstName")) inFirstName = false;
		else if (localName.equalsIgnoreCase("lastName")) inLastName = false;
		else if (localName.equalsIgnoreCase("team")) inTeam = false;
		else if (localName.equalsIgnoreCase("age")) inAge = false;
		else if (localName.equalsIgnoreCase("country_origin")) inCountry_Origin = false;
		else if (localName.equalsIgnoreCase("position")) inPosition = false;
		else if (localName.equalsIgnoreCase("pref_foot")) inPref_Foot = false;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String content = new String(ch, start, length);
		if (inTeam) currentPlayer.team = content;
		else if (inFirstName) currentPlayer.firstName = content;
		else if (inLastName) currentPlayer.lastName = content;
		else if (inAge) currentPlayer.age = content;
		else if (inCountry_Origin) currentPlayer.country_origin = content;
		else if (inPosition) currentPlayer.position = content;
		else if (inPref_Foot) currentPlayer.pref_foot = content;
	}
	
	public PlayersContainer getPlayersContainer() {
		return this.myPlayers;
	}
}
