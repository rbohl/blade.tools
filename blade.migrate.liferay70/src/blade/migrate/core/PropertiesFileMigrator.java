package blade.migrate.core;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

public abstract class PropertiesFileMigrator implements FileMigrator {

	ComponentContext context;
	String problemTitle;
	String problemUrl;
	String problemSummary;
	String problemType;
	String problemTickets;

	protected final List<String> properties = new ArrayList<String>();

	@Activate
	public void activate(ComponentContext ctx) {
		this.context = ctx;

		final Dictionary<String, Object> properties =
			this.context.getProperties();

		this.problemTitle = (String)properties.get("problem.title");
		this.problemUrl = (String)properties.get("problem.url");
		this.problemSummary = (String)properties.get("problem.summary");
		this.problemType = (String)properties.get("file.extensions");
		this.problemTickets = (String)properties.get("problem.tickets");
	}

	@Override
	public List<Problem> analyzeFile(File file) {
		final List<Problem> problems = new ArrayList<>();

		PropertiesFileChecker propertiesFileChecker =
			new PropertiesFileChecker(file);

		for (String key : properties) {
			List<SearchResult> results =
				propertiesFileChecker.findProperties(key);

			if (results != null) {
				for (SearchResult searchResult : results) {
					problems.add(new Problem(
						this.problemTitle, this.problemUrl, this.problemSummary,
						this.problemType, this.problemTickets, file,
						searchResult.startLine, searchResult.startOffset,
						searchResult.endOffset));
				}
			}
		}
		return problems;
	}

}
