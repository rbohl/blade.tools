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
			"file.extensions=java",
			"problem.title=Replaced ReservedUserIdException with UserIdException Inner Classes",
			"problem.summary=The ReservedUserIdException has been deprecated and replaced with UserIdException.MustNotBeReserved.",
			"problem.tickets=LPS-53487",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#replaced-reserveduseridexception-with-useridexception-inner-classes"
		},
		service = FileMigrator.class
	)

public class ReservedUserIdExceptionCatch  extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		List<SearchResult> results = javaFileChecker.findCatchExceptions(new String[]{"ReservedUserIdException"});
		System.out.println(results);
		return results;
	}
}
