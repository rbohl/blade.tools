package blade.migrate.liferay70;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
@Component(
	property = {
		"file.extensions=properties",
		"problem.title=Breadcrumb Portlet's Display Styles Changes",
		"problem.summary=Replaced the Breadcrumb Portlet's Display Styles with ADTs",
		"problem.tickets=LPS-53577",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#replaced-the-breadcrumb-portlets-display-styles-with-adts",
	},
	service = FileMigrator.class
)
public class BreadcrumbProperties extends PropertiesFileMigrator {

	@Override
	protected void addPropertiesToSearch(List<String> properties) {
		properties.add("breadcrumb.display.style.default");
		properties.add("breadcrumb.display.style.options");
	}

}