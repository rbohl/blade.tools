package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=properties",
		"problem.title=Asset Publisher Proerties Removed",
		"problem.summary=Removed the asset.publisher.asset.entry.query.processors Property",
		"problem.tickets=LPS-52966",
		"problem.section=#removed-the-assetpublisherassetentryqueryprocessors-property",
	},
	service = FileMigrator.class
)
public class AssetPublisherProperties extends PropertiesFileMigrator {

	@Override
	protected void addPropertiesToSearch(List<String> _properties) {
		_properties.add("asset.publisher.asset.entry.query.processors");
	}

}
