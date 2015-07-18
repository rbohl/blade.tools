
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
		"problem.title=Removed WikiUtil.getEntries Method",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-wikiutilgetentries-method",
		"problem.summary=Removed WikiUtil.getEntries Method",
		"problem.tickets=LPS-56242",
	},
	service = FileMigrator.class
)
public class WikiUtilGetEntriesInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findMethodInvocations(null, "WikiUtil",
				"getEntries", null);
	}

}
