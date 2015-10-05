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
		"problem.summary=Removed get and format Methods that Used PortletConfig Parameters",
		"problem.tickets=LPS-44342",
		"problem.title=PortletConfig get/format methods",
		"problem.section=#removed-get-and-format-methods-that-used-portletconfig-parameters"
	},
	service = FileMigrator.class
)
public class PortletConfigGetFormatMethods extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		// get methods
		List<SearchResult> invocations = javaFileChecker.findMethodInvocations(null, "LanguageUtil", "get",
				new String[] { "PortletConfig", "Locale", "String" });

		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations(null, "LanguageUtil", "get",
				new String[] { "PortletConfig", "Locale", "String", "String" });

		searchResults.addAll(invocations);

		//format methods
		invocations = javaFileChecker.findMethodInvocations(null, "LanguageUtil", "format",
				new String[] { "PortletConfig", "Locale", "String", "Object" });

		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations(null, "LanguageUtil", "format",
				new String[] { "PortletConfig", "Locale", "String", "Object", "boolean" });

		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations(null, "LanguageUtil", "format",
				new String[] { "PortletConfig", "Locale", "String", "Object[]" });

		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations(null, "LanguageUtil", "format",
				new String[] { "PortletConfig", "Locale", "String", "Object[]", "boolean" });

		searchResults.addAll(invocations);

		return searchResults;
	}
}