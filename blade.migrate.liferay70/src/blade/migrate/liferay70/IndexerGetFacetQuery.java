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
		"problem.summary=Replaced Method getFacetQuery with getFacetBooleanFilter in Indexer",
		"problem.tickets=LPS-56064",
		"problem.title=Indexer API Changes",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#replaced-method-getpermissionquery-with-getpermissionfilter-in-searchpermissionchecker-and-getfacetquery-with-getfacetbooleanfilter-in-indexer"
	},
	service = FileMigrator.class
)
public class IndexerGetFacetQuery extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		final List<SearchResult> declaration = javaFileChecker.findMethodDeclaration("getFacetQuery",
				new String[] { "String", "SearchContextPortletURL" });

		searchResults.addAll(declaration);

		final List<SearchResult> invocations = javaFileChecker.findMethodInvocations("Indexer", null, "getFacetQuery",
				null);

		searchResults.addAll(invocations);

		return searchResults;
	}
}