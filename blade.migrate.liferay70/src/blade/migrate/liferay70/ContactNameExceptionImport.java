package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java",
		"problem.title=Moved the Contact Name Exception Classes to Inner Classes of ContactNameException",
		"problem.summary=The use of classes ContactFirstNameException, ContactFullNameException, and ContactLastNameException has been moved to inner classes in a new class called ContactNameException.",
		"problem.tickets=LPS-55364",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#moved-the-contact-name-exception-classes-to-inner-classes-of-contactnameexception"
	},
	service = FileMigrator.class
)
public class ContactNameExceptionImport extends JavaFileMigrator {

	private final static String[] imports = new String[] {
		"com.liferay.portal.ContactFirstNameException",
		"com.liferay.portal.ContactFullNameException",
		"com.liferay.portal.ContactLastNameException"
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		for (String importName : imports) {
			final SearchResult importResult = javaFileChecker.findImport(importName);

			if (importResult != null) {
				searchResults.add(importResult);
			}
		}

		return searchResults;

	}

}
