package blade.migrate.core;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

/**
 * @author Andy Wu
 */
public abstract class XMLFileMigrator implements FileMigrator {

	@Activate
	public void activate(ComponentContext ctx) {
		_context = ctx;

		final Dictionary<String, Object> properties = _context.getProperties();

		_problemTitle = (String)properties.get("problem.title");
		_problemSummary = (String)properties.get("problem.summary");
		_problemType = (String)properties.get("file.extensions");
		_problemTickets = (String)properties.get("problem.tickets");
		_sectionKey = (String)properties.get("problem.section");
	}

	@Override
	public List<Problem> analyze(File file) {
		final List<Problem> problems = new ArrayList<>();

		final List<SearchResult> searchResults = searchXMLFile(file);

		if (searchResults != null) {
			String sectionHtml = MarkdownParser.getSection("BREAKING_CHANGES.markdown", _sectionKey);

			for (SearchResult searchResult : searchResults) {
				Problem problem = new Problem(
						_problemTitle, _problemSummary,
						_problemType, _problemTickets, file,
						searchResult.startLine, searchResult.startOffset,
						searchResult.endOffset, sectionHtml, searchResult.autoCorrectContext);

				problems.add(problem);
			}
		}

		return problems;
	}

	protected abstract List<SearchResult> searchXMLFile(File file);

	private ComponentContext _context;
	protected String _problemSummary;
	protected String _problemTickets;
	protected String _problemTitle;
	protected String _problemType;
	protected String _sectionKey = "";

}