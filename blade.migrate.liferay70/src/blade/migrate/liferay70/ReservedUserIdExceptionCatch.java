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
		"problem.title=Replaced ReservedUserIdException with UserIdException Inner Classes",
		"problem.summary=The ReservedUserIdException has been deprecated and replaced with UserIdException.MustNotBeReserved.",
		"problem.tickets=LPS-53487",
		"problem.section=#replaced-reserveduseridexception-with-useridexception-inner-classes"
	},
	service = FileMigrator.class
)
public class ReservedUserIdExceptionCatch  extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findCatchExceptions(new String[]{"ReservedUserIdException"});
	}
}
