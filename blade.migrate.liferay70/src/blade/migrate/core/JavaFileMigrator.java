package blade.migrate.core;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

public abstract class JavaFileMigrator implements FileMigrator {

	ComponentContext context;
	String _problemTitle;
	String _problemSummary;
	String _problemTickets;
	List<String> _fileExtentions;
	String _sectionKey;

	@Activate
	public void activate(ComponentContext ctx) {
		this.context = ctx;

		final Dictionary<String, Object> properties = this.context.getProperties();

		_fileExtentions = Arrays.asList(((String)properties.get("file.extensions")).split(","));

		_problemTitle = (String)properties.get("problem.title");
		_problemSummary = (String)properties.get("problem.summary");
		_problemTickets = (String)properties.get("problem.tickets");
		_sectionKey = (String)properties.get("problem.section");
	}

	@Override
	public List<Problem> analyze(File file) {
		final List<Problem> problems = new ArrayList<>();

		final List<SearchResult> searchResults = searchJavaFile(file, createJavaFileChecker(file));

		if (searchResults != null) {
			String sectionHtml = MarkdownParser.getSection("BREAKING_CHANGES.markdown", _sectionKey);

			for (SearchResult searchResult : searchResults) {
				String fileExtension = new Path(file.getAbsolutePath()).getFileExtension();

				problems.add(new Problem(_problemTitle, _problemSummary,
					fileExtension, _problemTickets, file, searchResult.startLine,
					searchResult.startOffset, searchResult.endOffset, sectionHtml,
					searchResult.autoCorrectContext));
			}
		}

		return problems;
	}

	protected JavaFileChecker createJavaFileChecker(File file) {
		final String fileName = file.getName();

		if (fileName.endsWith("java") && _fileExtentions.contains("java")) {
			return new JavaFileChecker(file);
		}
		else if ((fileName.endsWith("jsp") && _fileExtentions.contains("jsp"))
				|| (fileName.endsWith("jspf") && _fileExtentions.contains("jspf"))) {
			return new JSPFileChecker(file);
		}

		return null;
	}

	protected abstract List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker);

}
