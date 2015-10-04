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
		"problem.summary=All Dynamic Data Mapping APIs previously exposed as Liferay Portal API in 6.2 have been move out from portal-service into separate OSGi modules",
		"problem.tickets=LPS-57255",
		"problem.title=Dynamic Data Mapping APIs migrated to OSGi module",
		"problem.section="
	},
	service = FileMigrator.class
)
public class DDMLegacyAPI extends JavaFileMigrator {

	private static final String[] SERVICE_API_PREFIXES = {
		"com.liferay.portlet.dynamicdatamapping.service.DDMContent",
		"com.liferay.portlet.dynamicdatamapping.service.DDMStorageLink",
		"com.liferay.portlet.dynamicdatamapping.service.DDMStructureLink",
		"com.liferay.portlet.dynamicdatamapping.service.DDMStructure",
		"com.liferay.portlet.dynamicdatamapping.service.DDMTemplate"
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findServiceAPIs(SERVICE_API_PREFIXES);
	}

}
