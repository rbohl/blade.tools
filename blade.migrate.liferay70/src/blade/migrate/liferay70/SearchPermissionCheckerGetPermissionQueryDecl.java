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
		"file.extension=java",
		"problem.summary=Replaced Method getPermissionQuery with getPermissionFilter in SearchPermissionChecker",
		"problem.tickets=LPS-56064",
		"problem.title=Replaced Method getPermissionQuery", "problem.type=java",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#replaced-method-getpermissionquery-with-getpermissionfilter-in-searchpermissionchecker-and-getfacetquery-with-getfacetbooleanfilter-in-indexer"
	},
	service = FileMigrator.class
)
public class SearchPermissionCheckerGetPermissionQueryDecl extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

		return javaFileChecker.findMethodDeclaration(
				"getPermissionQuery",
				new String[] {"long", "long[]", "long", "String",
						"Query", "SearchContext"});
	}
}