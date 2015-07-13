package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.PropertiesFileChecker;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

public abstract class PropertiesFileMigrator implements FileMigrator {

	private ComponentContext _context;
	private String _problemTitle;
	private String _problemUrl;
	private String _problemSummary;
	private String _problemType;
	private String _problemTickets;
	final List<String> _properties = new ArrayList<String>();

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

		addPropertiesToSearch(this._properties);
	}

	protected abstract void addPropertiesToSearch(List<String> _properties);

	@Override
	public List<Problem> analyzeFile(File file) {
		final List<Problem> problems = new ArrayList<>();

		PropertiesFileChecker propertiesFileChecker =
			new PropertiesFileChecker(file);

		for (String key : _properties) {
			List<SearchResult> results =
				propertiesFileChecker.findProperties(key);

			if (results != null) {
				for (SearchResult searchResult : results) {
					problems.add(new Problem(
						this._problemTitle, this._problemUrl, this._problemSummary,
						this._problemType, this._problemTickets, file,
						searchResult.startLine, searchResult.startOffset,
						searchResult.endOffset));
				}
			}
		}

		return problems;
	}

}
