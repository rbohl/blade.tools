package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.ImportStatementMigrator;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java",
		"problem.title=Moved the Contact Name Exception Classes to Inner Classes of ContactNameException",
		"problem.summary=The use of classes ContactFirstNameException, ContactFullNameException, and ContactLastNameException has been moved to inner classes in a new class called ContactNameException.",
		"problem.tickets=LPS-55364",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#moved-the-contact-name-exception-classes-to-inner-classes-of-contactnameexception",
		"auto.correct=imports"
	},
	service = FileMigrator.class
)
public class ContactNameExceptionImport extends ImportStatementMigrator {

	private final static String[] IMPORTS = new String[] {
		"com.liferay.portal.ContactFirstNameException",
		"com.liferay.portal.ContactFullNameException",
		"com.liferay.portal.ContactLastNameException"
	};

	private final static String[] IMPORTS_FIXED = new String[] {
		"com.liferay.portal.ContactFirstNameException",
		"com.liferay.portal.ContactFullNameException",
		"com.liferay.portal.ContactLastNameException"
	};

	public ContactNameExceptionImport() {
		super(IMPORTS, IMPORTS_FIXED);
	}

}
