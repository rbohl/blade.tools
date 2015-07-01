
package blade.migrate.liferay70;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
	property = {
		"file.extension=java",
		"method.expression=WikiUtil",
		"method.type=invocation",
		"method.name=getEntries",
		"problem.title=Removed WikiUtil.getEntries Method",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-wikiutilgetentries-method",
		"problem.summary=Removed WikiUtil.getEntries Method",
		"problem.type=java,jsp",
		"problem.tickets=LPS-56242",
	},
	service = FileMigrator.class
)
public class WikiUtilGetEntriesInvocation extends JavaMethodMigrator {
}
