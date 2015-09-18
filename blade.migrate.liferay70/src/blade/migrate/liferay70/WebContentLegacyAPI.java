package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java,jsp,jspf",
		"problem.summary=All Web Content APIs previously exposed as Liferay Portal API in 6.2 have been move out from portal-service into separate OSGi modules",
		"problem.tickets=LPS-54838",
		"problem.title=Web Content APIs migrated to OSGi module",
		"problem.url="
	},
	service = FileMigrator.class
)
public class WebContentLegacyAPI extends JavaFileMigrator {

	private static final String[] PREFIXES =  {
		"com.liferay.portlet.journal.service.JournalArticle",
		"com.liferay.portlet.journal.service.JournalArticleImage",
		"com.liferay.portlet.journal.service.JournalArticleResource",
		"com.liferay.portlet.journal.service.JournalContentSearch,",
		"com.liferay.portlet.journal.service.JournalFeed",
		"com.liferay.portlet.journal.service.JournalFolder",
		"com.liferay.portlet.journal.service.JournalStructure",
		"com.liferay.portlet.journal.service.JournalStructure",
		"com.liferay.portlet.journal.service.JournalTemplate",
	};

	private static final String[] SUFFIXES =  {
		"LocalService",
		"LocalServiceUtil",
		"LocalServiceWrapper",
		"Service",
		"ServiceUtil",
		"ServiceWrapper",
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {

		return javaFileChecker.findMigratorService(PREFIXES, SUFFIXES);
	}
}