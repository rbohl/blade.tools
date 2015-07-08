
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
		"file.extensions=java",
		"problem.title=MBMessageService API Changes",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-permissionclassname-permissionclasspk-and-permissionowner-parameters-from-mbmessage-api",
		"problem.summary=Removed permissionClassName, permissionClassPK, and permissionOwner Parameters from MBMessage API",
		"problem.tickets=LPS-55877",
	},
	service = FileMigrator.class
)
public class MBMessageServiceUtilInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
	    final List<SearchResult> result = new ArrayList<SearchResult>();

	    final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

        result.addAll( javaFileChecker.findMethodInvocations(null,
            "MBMessageServiceUtil", "addDiscussionMessage", null) ) ;

        result.addAll( javaFileChecker.findMethodInvocations(null,
            "MBMessageServiceUtil", "deleteDiscussionMessage", null) ) ;

        result.addAll( javaFileChecker.findMethodInvocations(null,
            "MBMessageServiceUtil", "updateDiscussionMessage", null) ) ;

		return result;
	}

}
