package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
@Component(
	property = {
		"file.extensions=java,jsp,jspf",
		"problem.title=Web Content Articles Now Require a Structure and Template",
		"problem.summary=Web content is now required to use a structure and template. " +
			"A default structure and template named Basic Web Content was " +
			"added to the global scope, and can be modified or deleted.",
		"problem.tickets=LPS-45107",
		"problem.section=#web-content-articles-now-require-a-structure-and-template"
	},
	service = FileMigrator.class
)
public class WebContentArticlesStrucAndTempl extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file,
			JavaFileChecker javaFileChecker) {
		// Journal API to create web content without a structure
		// or template are affected
		final List<SearchResult> searchResults = new ArrayList<>();

		List<SearchResult> journalArticleUtil = javaFileChecker
				.findMethodInvocations(null, "JournalArticleLocalServiceUtil",
						"addArticle", null);

		searchResults.addAll(journalArticleUtil);

		journalArticleUtil = javaFileChecker.findMethodInvocations(null,
				"JournalArticleServiceUtil", "addArticle", null);

		searchResults.addAll(journalArticleUtil);

		return searchResults;
	}

}
