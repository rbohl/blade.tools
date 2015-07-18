package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.List;

import org.osgi.service.component.annotations.Component;
@Component(
	property = {
		"file.extensions=java",
		"problem.summary=Changed the Indexer API to Include the PortletRequest and PortletResponse Parameters",
		"problem.tickets=LPS-44639,LPS-44894",
		"problem.title=Indexer API Changes",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters"
	},
	service = FileMigrator.class
)
public class IndexerDoGetSummaryDecl extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findMethodDeclaration("doGetSummary", new String[] {"Document", "Locale", "String", "PortletURL"});
	}
}