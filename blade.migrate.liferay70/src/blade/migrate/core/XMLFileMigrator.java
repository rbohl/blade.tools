package blade.migrate.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;

public abstract class XMLFileMigrator implements FileMigrator {

	ComponentContext _context;
	String _problemTitle;
	String _problemUrl;
	String _problemSummary;
	String _problemType;
	String _problemTickets;

	@Activate
	public void activate(ComponentContext ctx) {
		this._context = ctx;

		final Dictionary<String, Object> properties =
			this._context.getProperties();

		this._problemTitle = (String)properties.get("problem.title");
		this._problemUrl = (String)properties.get("problem.url");
		this._problemSummary = (String)properties.get("problem.summary");
		this._problemType = (String)properties.get("file.extensions");
		this._problemTickets = (String)properties.get("problem.tickets");
	}

	@Override
	public List<Problem> analyzeFile(File file)  {
		final List<Problem> problems = new ArrayList<>();

		final List<SearchResult> searchResults = searchXMLFile(file);

		if (searchResults != null) {
			for (SearchResult searchResult : searchResults) {
				problems.add(new Problem(this._problemTitle, this._problemUrl, this._problemSummary,
					this._problemType, this._problemTickets, file, searchResult.startLine,
					searchResult.startOffset, searchResult.endOffset));
			}
		}

		return problems;
	}

	protected abstract List<SearchResult> searchXMLFile(File file) ;

}
