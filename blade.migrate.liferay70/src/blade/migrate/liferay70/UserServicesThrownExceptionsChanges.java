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
		"problem.title=Changes in Exceptions Thrown by User Services",
		"problem.section=#changes-in-exceptions-thrown-by-user-services",
		"problem.summary=Changes in Exceptions Thrown by User Services",
		"problem.tickets=LPS-47130",
	},
	service = FileMigrator.class
)
public class UserServicesThrownExceptionsChanges extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findCatchExceptions(new String[] {"DuplicateUserScreenNameException", "DuplicateUserEmailAddressException"});
	}
}