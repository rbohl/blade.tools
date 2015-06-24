
package blade.migrate.liferay70;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
	property = {
		"file.extension=java",
		"method.type=declaration",
		"method.name=doGetSummary",
		"method.param.types=Document,Locale,String,PortletURL",
		"problem.title=Indexer API Changes",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters",
		"problem.summary=Changed the Indexer API to Include the PortletRequest and PortletResponse Parameters",
		"problem.type=java",
		"problem.tickets=LPS-44639,LPS-44894",
	},
	service = FileMigrator.class
)
public class IndexerDoGetSummaryDecl extends JavaMethodMigrator {
}
