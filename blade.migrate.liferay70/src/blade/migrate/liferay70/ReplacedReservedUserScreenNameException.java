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
		"problem.title=Replaced the ReservedUserScreenNameException with UserScreenNameException.MustNotBeReserved in UserLocalService",
		"problem.section=#replaced-the-reserveduseremailaddressexception-with-useremailaddressexception-inner-classes-in-user-services",
		"problem.summary=Replaced the ReservedUserScreenNameException with UserScreenNameException.MustNotBeReserved in UserLocalService",
		"problem.tickets=LPS-53113",
	},
	service = FileMigrator.class
)
public class ReplacedReservedUserScreenNameException extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findCatchExceptions(new String[] {"ReservedUserScreenNameException"});
	}
}