
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
public class XMLFileChecker {

	public XMLFileChecker(File file) {

		this.file = file;
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			parser = factory.newSAXParser();
		}
		catch (ParserConfigurationException | SAXException e) {
			new IllegalArgumentException(e);
		}
	}

	public List<SearchResult> findTag(String tagName, String value)
		throws IOException, ParserConfigurationException, SAXException {

		List<SearchResult> results = new ArrayList<>();

		// start parsing the given file and generates results

		this.parser.parse(
			this.file, new SearchExecutor(tagName, value, results, file));
		return results;
	}

	private File file;
	private SAXParser parser;

}

class SearchExecutor extends DefaultHandler {

	public SearchExecutor(
		String tagName, String value, List<SearchResult> results, File file) {

		super();
		this.tagName = tagName;
		this.value = value;
		this.results = results;
		this.file = file;
	}

	@Override
	public void setDocumentLocator(final Locator locator) {

		this.locator = locator;
	}

	@Override
	public void startDocument()
		throws SAXException {

		// init the result list

	}

	@Override
	public void startElement(
		String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

		if (tagName.equals(qName)) {
			inState = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
		throws SAXException {

		// reset the state when goes to end of each element

		inState = false;
	}

	@Override
	public void characters(char[] ch, int start, int length)
		throws SAXException {

		String content = new String(ch, start, length);

		if (inState && value.equals(content)) {
			results.add(
				new SearchResult(
					file,
					0, 0, locator.getLineNumber(), locator.getLineNumber()));
		}
	}

	private String tagName;
	private String value;

	// is in the target Tag

	private boolean inState = false;
	private Locator locator;
	private List<SearchResult> results = null;
	private File file;
}