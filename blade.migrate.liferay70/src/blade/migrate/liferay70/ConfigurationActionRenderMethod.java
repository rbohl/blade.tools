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
		"file.extensions=java",
		"problem.summary=Removed render Method from ConfigurationAction API",
		"problem.tickets=LPS-56300",
		"problem.title=ConfigurationAction render method",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-render-method-from-configurationaction-api"
	},
	service = FileMigrator.class
)
public class ConfigurationActionRenderMethod extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		// render method declarations
		List<SearchResult> declarations = javaFileChecker.findMethodDeclaration("render",
				new String[] { "PortletConfig", "RenderRequest", "RenderResponse" });

		searchResults.addAll(declarations);

		// render method invocations
		List<SearchResult> invocations = javaFileChecker.findMethodInvocations(
				"ConfigurationAction", null, "render", null);

		searchResults.addAll(invocations);

		return searchResults;
	}
}