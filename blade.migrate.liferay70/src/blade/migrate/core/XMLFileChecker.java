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
		_file = file;

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			_parser = factory.newSAXParser();
		}
		catch (ParserConfigurationException | SAXException e) {
			new IllegalArgumentException(e);
		}
	}

	public List<SearchResult> findTag(String tagName, String value)
		throws IOException, ParserConfigurationException, SAXException {

		// start parsing the given file and generates results

		SearchExecutor searcher = new SearchExecutor(tagName, value);

		_parser.parse(_file, searcher);

		return searcher.getResults();
	}

	private File _file;
	private SAXParser _parser;

	class SearchExecutor extends DefaultHandler {

		public SearchExecutor(String tagName, String value) {
			super();

			_tagName = tagName;
			_value = value;
			_results = new ArrayList<>();
		}

		public List<SearchResult> getResults() {
			return _results;
		}

		@Override
		public void setDocumentLocator(final Locator locator) {
			this.locator = locator;
		}

		@Override
		public void startDocument()
			throws SAXException {
			_results.clear();
		}

		@Override
		public void startElement(
			String uri, String localName, String qName, Attributes attributes)
				throws SAXException {

			if (_tagName.equals(qName)) {
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

			if (inState && _value.equals(content)) {
				_results.add(
					new SearchResult(
						_file,
						0, 0, locator.getLineNumber(), locator.getLineNumber()));
			}
		}

		private String _tagName;
		private String _value;
		// is in the target Tag
		private boolean inState = false;
		private Locator locator;
		private List<SearchResult> _results = null;
	}

}