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
		"problem.summary=All Calendar APIs previously exposed as Liferay Portal API in 6.2 have been move out from portal-service into separate OSGi modules",
		"problem.tickets=LPS-55026",
		"problem.title=Calendar APIs migrated to OSGi module",
		"problem.section="
	},
	service = FileMigrator.class
)
public class CalendarLegacyAPI extends JavaFileMigrator {

	private static final String[] SERVICE_API_PREFIXES =  {
		"com.liferay.calendar.service.CalendarBooking",
		"com.liferay.calendar.service.CalendarImporter",
		"com.liferay.calendar.service.Calendar",
		"com.liferay.calendar.service.CalendarNotificationTemplate",
		"com.liferay.calendar.service.CalendarResource"
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findServiceAPIs(SERVICE_API_PREFIXES);
	}
}
