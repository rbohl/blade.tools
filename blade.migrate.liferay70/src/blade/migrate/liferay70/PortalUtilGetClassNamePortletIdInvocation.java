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
		"problem.title=Removed the getClassNamePortletId(String) Method from PortalUtil Class",
		"problem.section=#removed-the-getclassnameportletidstring-method-from-portalutil-class",
		"problem.summary=Removed the getClassNamePortletId(String) Method from PortalUtil Class",
		"problem.tickets=LPS-50604",
	},
	service = FileMigrator.class
)
public class PortalUtilGetClassNamePortletIdInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findMethodInvocations(null, "PortalUtil",
				"getClassNamePortletId", null);
	}

}
