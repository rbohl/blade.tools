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
			"problem.title=Added New Methods in the ScreenNameValidator Interface",
			"problem.summary=The ScreenNameValidator interface has new methods getDescription(Locale) and getJSValidation().",
			"problem.tickets=LPS-53409",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#added-new-methods-in-the-screennamevalidator-interface"
		},
		service = FileMigrator.class
	)

public class ScreenNameValidatorInterface extends JavaFileMigrator {
	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		return javaFileChecker.findImplementsInterface("ScreenNameValidator");
	}
}
