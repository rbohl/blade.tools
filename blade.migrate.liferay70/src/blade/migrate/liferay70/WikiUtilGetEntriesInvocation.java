
package blade.migrate.liferay70;

import java.io.File;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

@Component(
	property = {
		"file.extension=java",
		"problem.title=Removed WikiUtil.getEntries Method",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-wikiutilgetentries-method",
		"problem.summary=Removed WikiUtil.getEntries Method",
		"problem.type=java,jsp",
		"problem.tickets=LPS-56242",
	},
	service = FileMigrator.class
)
public class WikiUtilGetEntriesInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

		return javaFileChecker.findMethodInvocations("WikiUtil", "getEntries");
	}

}
