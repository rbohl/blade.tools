package blade.migrate.liferay70;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
	property = {
		"file.extensions=properties",
		"problem.title=Convert Process Classes Are No Longer Specified via the convert.processes Portal Property, but Are Contributed as OSGi Modules",
		"problem.summary=The implementation class com.liferay.portal.convert.ConvertProcess was renamed com.liferay.portal.convert.BaseConvertProcess. "
                                        + "An interface named com.liferay.portal.convert.ConvertProcess was created for it."
                                        + " The convert.processes key was removed from portal.properties. "
                                        + "Consequentially, ConvertProcess implementations must register as OSGi components.",
		"problem.tickets=LPS-50604",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#convert-process-classes-are-no-longer-specified-via-the-convertprocesses-portal-property-but-are-contributed-as-osgi-modules"
	},
	service = FileMigrator.class
)
public class ConvertProcessProperties extends PropertiesFileMigrator {

	@Override
	protected void addPropertiesToSearch(List<String> properties) {
		properties.add("convert.processes");
	}

}
