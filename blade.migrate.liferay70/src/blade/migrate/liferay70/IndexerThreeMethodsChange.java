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
		"problem.title=Changes to Indexer methods",
		"problem.summary=Method Indexer.addRelatedEntryFields(Document, Object) has been moved into RelatedEnt" +
			"ryIndexer. Indexer.reindexDDMStructures(List<Long>) has been moved into DDMStructureIndexer." +
			" Indexer.getQueryString(SearchContext, Query) has been removed, in favor of calling SearchEngineUtil." +
			"getQueryString(SearchContext, Query)",
		"problem.tickets=LPS-55928",
		"problem.section=#moved-indexer"
	},
	service = FileMigrator.class
)
public class IndexerThreeMethodsChange  extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		List<SearchResult> declarations = javaFileChecker.findMethodDeclaration(
				"addRelatedEntryFields", new String[] { "Document", "Object" });
		searchResults.addAll(declarations);

		declarations = javaFileChecker.findMethodDeclaration(
				"reindexDDMStructures", new String[] { "List<Long>" });
		searchResults.addAll(declarations);

		declarations = javaFileChecker.findMethodDeclaration("getQueryString",
				new String[] { "SearchContext", "Query" });
		searchResults.addAll(declarations);

		List<SearchResult> invocations = javaFileChecker.findMethodInvocations(
				"Indexer", null, "addRelatedEntryFields",
				new String[] { "Document", "Object" });
		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations("indexer", null,
				"addRelatedEntryFields", new String[] { "Document", "Object" });
		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations("Indexer", null,
				"reindexDDMStructures", new String[] { "List<Long>" });
		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations("indexer", null,
				"reindexDDMStructures", new String[] { "List<Long>" });
		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations("Indexer", null,
				"getQueryString", new String[] { "SearchContext", "Query" });
		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations("indexer", null,
				"getQueryString", new String[] { "SearchContext", "Query" });
		searchResults.addAll(invocations);

		return searchResults;
	}
}
