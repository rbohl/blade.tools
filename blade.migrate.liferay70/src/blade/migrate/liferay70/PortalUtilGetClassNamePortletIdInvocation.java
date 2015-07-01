
package blade.migrate.liferay70;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
	property = {
		"file.extension=java",
		"method.expression=PortalUtil",
		"method.type=invocation",
		"method.name=getClassNamePortletId",
		"problem.title=Removed the getClassNamePortletId(String) Method from PortalUtil Class",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-the-getclassnameportletidstring-method-from-portalutil-class",
		"problem.summary=Removed the getClassNamePortletId(String) Method from PortalUtil Class",
		"problem.type=java,jsp",
		"problem.tickets=LPS-50604",
	},
	service = FileMigrator.class
)
public class PortalUtilGetClassNamePortletIdInvocation extends JavaMethodMigrator {
}
