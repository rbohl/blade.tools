package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.PropertiesFileChecker;
import blade.migrate.core.SearchResult;
@Component(property = { "file.extensions=properties" })
public class UsersLastNameRequiredProperties implements FileMigrator {

	private final List<String> properties = new ArrayList<>();

	private String title = "Removed USERS_LAST_NAME_REQUIRED from portal.properties "
									+ "in Favor of language.properties Configurations";
	private String summary = "The USERS_LAST_NAME_REQUIRED property has been removed "
											+ "from portal.properties and the corresponding UI. Required names are now handled "
											+ "on a per-language basis via the language.properties files. It has also been removed as "
											+ "an option from the Portal Settings section of the Control Panel.";
	private String tickets = "LPS-54956";
	private String url = "https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-users_last_name_required-from-portalproperties-in-favor-of-languageproperties-configurations";
	private String type = "java,properties";


	public UsersLastNameRequiredProperties() {
		properties.add("users.last.name.required");
	}

	@Override
	public List<Problem> analyzeFile( File file )
	{
		PropertiesFileChecker propertiesFileChecker = new PropertiesFileChecker(file);

		final List<Problem> problems = new ArrayList<>();

		for (String key : properties) {
			List<SearchResult> searchResults = propertiesFileChecker.findProperties(key);

			if (searchResults != null) {
				for (SearchResult searchResult : searchResults) {
					problems.add(new Problem(title, url, summary, type, tickets,
							file, searchResult.startLine, searchResult.startOffset, searchResult.endOffset));
				}
			}
		}

		return problems;
	}
}
