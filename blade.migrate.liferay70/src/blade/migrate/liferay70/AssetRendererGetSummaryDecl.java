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
		"file.extensions=java,jsp,jspf",
		"problem.summary=Changed the AssetRenderer API to Include the PortletRequest and PortletResponse Parameters",
		"problem.tickets=LPS-44639,LPS-44894",
		"problem.title=AssetRenderer API Changes",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters"
	},
	service = FileMigrator.class
)
public class AssetRendererGetSummaryDecl extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findMethodDeclaration("getSummary",
				new String[] { "Locale" });
	}
}