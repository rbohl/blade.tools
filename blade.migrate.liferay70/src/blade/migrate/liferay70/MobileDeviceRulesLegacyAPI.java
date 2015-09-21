package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java,jsp,jspf",
		"problem.summary=All Mobile Device Rules APIs previously exposed as Liferay Portal API in 6.2 have been move out from portal-service into separate OSGi modules",
		"problem.tickets=LPS-57519",
		"problem.title=Mobile Device Rules APIs migrated to OSGi module",
		"problem.url="
	},
	service = FileMigrator.class
)
public class MobileDeviceRulesLegacyAPI extends JavaFileMigrator {

	private static final String[] SERVICE_API_PREFIXES =  {
		"com.liferay.portlet.mobiledevicerules.service.MDRAction",
		"com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstance",
		"com.liferay.portlet.mobiledevicerules.service.MDRRuleGroup",
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findServiceAPIs(SERVICE_API_PREFIXES);
	}
}