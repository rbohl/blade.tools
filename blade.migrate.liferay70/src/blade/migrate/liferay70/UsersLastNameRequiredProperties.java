package blade.migrate.liferay70;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
@Component(
	property = {
		"file.extensions=properties",
		"problem.title=Removed USERS_LAST_NAME_REQUIRED from portal.properties",
		"problem.summary=The USERS_LAST_NAME_REQUIRED property has been removed "
				+ "from portal.properties and the corresponding UI. Required names are now handled "
				+ "on a per-language basis via the language.properties files. It has also been removed as "
				+ "an option from the Portal Settings section of the Control Panel.",
		"problem.tickets=LPS-54956",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-users_last_name_required-from-portalproperties-in-favor-of-languageproperties-configurations",
	},
	service = FileMigrator.class
)
public class UsersLastNameRequiredProperties extends PropertiesFileMigrator {

	@Override
	protected void addPropertiesToSearch(List<String> _properties) {
		_properties.add("users.last.name.required");
	}

}
