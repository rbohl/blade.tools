package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.PropertiesFileChecker;
import blade.migrate.core.SearchResult;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
@Component(property = { "file.extension=properties" })
public class PortalProperties implements FileMigrator {

	private final List<String> properties = new ArrayList<>();

	public PortalProperties() {
		properties.add("company.settings.form.configuration");
		properties.add("company.settings.form.identification");
		properties.add("company.settings.form.miscellaneous");
		properties.add("company.settings.form.social");
		properties.add("layout.form.add");
		properties.add("layout.form.update");
		properties.add("layout.set.form.update");
		properties.add("organizations.form.add.identification");
		properties.add("organizations.form.add.main");
		properties.add("organizations.form.add.miscellaneous");
		properties.add("organizations.form.update.identification");
		properties.add("organizations.form.update.main");
		properties.add("organizations.form.update.miscellaneous");
		properties.add("sites.form.add.advanced");
		properties.add("sites.form.add.main");
		properties.add("sites.form.add.miscellaneous");
		properties.add("sites.form.add.seo");
		properties.add("sites.form.update.advanced");
		properties.add("sites.form.update.main");
		properties.add("sites.form.update.miscellaneous");
		properties.add("sites.form.update.seo");
		properties.add("users.form.add.identification");
		properties.add("users.form.add.main");
		properties.add("users.form.add.miscellaneous");
		properties.add("users.form.my.account.identification");
		properties.add("users.form.my.account.main");
		properties.add("users.form.my.account.miscellaneous");
		properties.add("users.form.update.identification");
		properties.add("users.form.update.main");
		properties.add("users.form.update.miscellaneous");
	}

	@Override
	public List<Problem> analyzeFile( File file )
	{
		PropertiesFileChecker propertiesFileChecker = new PropertiesFileChecker(
			file);
		final List<Problem> problems = new ArrayList<>();

		for (String key : properties) {
			SearchResult searchResult = propertiesFileChecker.findProperty(key);

			if (searchResult != null) {
				problems.add(new Problem("", "https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-portal-properties-used-to-display-sections-in-form-navigators",
						"Removed Portal Properties Used to Display Sections in Form Navigators",
						"java,properties", "LPS-54903", file, -1, -1, -1));
			}
		}

		return problems;
	}

}