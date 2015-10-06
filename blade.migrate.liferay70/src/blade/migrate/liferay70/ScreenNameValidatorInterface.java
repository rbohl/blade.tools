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
		"problem.title=Added New Methods in the ScreenNameValidator Interface",
		"problem.summary=The ScreenNameValidator interface has new methods getDescription(Locale) and getJSValidation().",
		"problem.tickets=LPS-53409",
		"problem.section=#added-new-methods-in-the-screennamevalidator-interface"
	},
	service = FileMigrator.class
)
public class ScreenNameValidatorInterface extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findImplementsInterface("ScreenNameValidator");
	}
}
