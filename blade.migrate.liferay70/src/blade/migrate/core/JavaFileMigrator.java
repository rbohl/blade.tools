package blade.migrate.core;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;

import java.io.File;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

public abstract class JavaFileMigrator implements FileMigrator {

	ComponentContext context;
	String problemTitle;
	String problemUrl;
	String problemSummary;
	String problemType;
	String problemTickets;

	@Activate
	public void activate(ComponentContext ctx) {
		this.context = ctx;

		final Dictionary<String, Object> properties =
			this.context.getProperties();

		this.problemTitle = (String)properties.get("problem.title");
		this.problemUrl = (String)properties.get("problem.url");
		this.problemSummary = (String)properties.get("problem.summary");
		this.problemType = (String)properties.get("problem.type");
		this.problemTickets = (String)properties.get("problem.tickets");
	}

	@Override
	public List<Problem> analyzeFile(File file) {
		final List<Problem> problems = new ArrayList<>();

		final List<SearchResult> searchResults = searchJavaFile(file);

		if (searchResults != null) {
			for (SearchResult searchResult : searchResults) {
				problems.add(new Problem(this.problemTitle, this.problemUrl, this.problemSummary,
					this.problemType, this.problemTickets, file, searchResult.startLine,
					searchResult.startOffset, searchResult.endOffset));
			}
		}

		return problems;
	}

	protected abstract List<SearchResult> searchJavaFile(File file);

}
