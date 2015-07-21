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
		"problem.summary=The method render has been removed from the interfaces AssetRenderer and WorkflowHandler.",
		"problem.tickets=LPS-56705",
		"problem.title=Removed render Method from AssetRenderer API and WorkflowHandler API",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-render-method-from-assetrenderer-api-and-workflowhandler-api"
	},
	service = FileMigrator.class
)
public class AssetRendererAndWorkflowHandlerRenderInvocation
		extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file,
			JavaFileChecker javaFileChecker) {

		final List<SearchResult> searchResults = new ArrayList<SearchResult>();
		final String[] assetRendererArgTypes = new String[] { "RenderRequest",
				"RenderResponse", "String" };
		final String[] workflowHandlerArgTypes = new String[] { "long",
				"RenderRequest", "RenderResponse", "String" };

		// render method declarations
		List<SearchResult> declarations = javaFileChecker
				.findMethodDeclaration("render", assetRendererArgTypes);
		searchResults.addAll(declarations);

		declarations = javaFileChecker.findMethodDeclaration("render",
				workflowHandlerArgTypes);
		searchResults.addAll(declarations);

		// render method invocations
		List<SearchResult> invocations = javaFileChecker.findMethodInvocations(
				"AssetRenderer", null, "render", assetRendererArgTypes);
		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations("WorkflowHandler",
				null, "render", workflowHandlerArgTypes);
		searchResults.addAll(invocations);

		return searchResults;
	}

}