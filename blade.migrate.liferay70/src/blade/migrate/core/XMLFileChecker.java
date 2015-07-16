package blade.migrate.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author andy wu
 *
 */
public class XMLFileChecker extends DefaultHandler {

	private File file;
	//is in the target Tag
	private boolean inState = false;
	private List<SearchResult> results = null;
	private String tagName;
	private String value;
	private Locator locator;
	private SAXParser parser;

	/**
	 *
	 * @param file
	 */
	public XMLFileChecker(File file){
		this.file = file;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	 @Override
     public void setDocumentLocator(final Locator locator) {
         this.locator = locator;
     }

	@Override
	public void startDocument() throws SAXException {
		//init the result list
		results = new ArrayList<SearchResult>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(tagName.equals(qName)){
			inState = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//reset the state when goes to end of each element
		inState = false;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
			String content = new String(ch, start, length);
			if( inState && value.equals(content)){
				results.add(new SearchResult(file, 0, 0, locator.getLineNumber(), locator.getLineNumber()));
			}
	}

	public List<SearchResult> findTag(String tagName, String value) throws SAXException, IOException, ParserConfigurationException {
		this.tagName = tagName;
		this.value  = value;
		//start parsing the given file and generates results
		this.parser.parse(this.file, this);
		return results;
	}

}
