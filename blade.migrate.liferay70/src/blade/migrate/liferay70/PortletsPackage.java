package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

@Component(
	property = {
		"file.extensions=java,jsp,jspf",
		"problem.title=Changed Java Package Names for Portlets Extracted as Modules",
		"problem.summary=The Java package names changed for portlets that were extracted as OSGi modules in 7.0.",
		"problem.tickets=LPS-56383",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-java-package-names-for-portlets-extracted-as-modules"
	},
	service = FileMigrator.class
)
public class PortletsPackage extends JavaFileMigrator {

	private final static String[] packages = new String[] {
		"com.liferay.portlet.bookmarks.service.persistence",
		"com.liferay.portlet.dynamicdatalists.service.persistence",
		"com.liferay.portlet.journal.service.persistence",
		"com.liferay.portlet.polls.service.persistence",
		"com.liferay.portlet.wiki.service.persistence"
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file,
			JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		for (String packageName : packages) {
			final SearchResult packageResult = javaFileChecker.findPackage(packageName);
			if (packageResult != null) {
				searchResults.add(packageResult);
			}
		}

		return searchResults;
	}

}
