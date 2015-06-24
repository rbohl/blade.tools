package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;

import org.osgi.service.component.annotations.Component;
@Component(
	property = {
		"file.extension=java", "method.name=doGetSummary",
		"method.param.types=Document,Locale,String,PortletURL",
		"method.type=declaration",
		"problem.summary=Changed the Indexer API to Include the PortletRequest and PortletResponse Parameters",
		"problem.tickets=LPS-44639,LPS-44894",
		"problem.title=Indexer API Changes", "problem.type=java",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters"
	},
	service = FileMigrator.class
)
public class IndexerDoGetSummaryDecl extends JavaMethodMigrator {
}