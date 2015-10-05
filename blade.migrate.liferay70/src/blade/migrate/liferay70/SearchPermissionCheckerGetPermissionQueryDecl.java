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
		"problem.summary=Replaced Method getPermissionQuery with getPermissionFilter in SearchPermissionChecker",
		"problem.tickets=LPS-56064",
		"problem.title=Replaced Method getPermissionQuery",
		"problem.section=#replaced-method-getpermissionquery-with-getpermissionfilter-in-searchpermissionchecker-and-getfacetquery-with-getfacetbooleanfilter-in-indexer"
	},
	service = FileMigrator.class
)
public class SearchPermissionCheckerGetPermissionQueryDecl extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findMethodDeclaration(
				"getPermissionQuery",
				new String[] {"long", "long[]", "long", "String",
						"Query", "SearchContext"});
	}
}