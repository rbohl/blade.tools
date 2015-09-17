package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JSPFileChecker;
import blade.migrate.core.JSPTagsFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=jsp,jspf",
		"problem.title=Changed the Usage of the liferay-ui:restore-entry Tag",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-usage-of-the-liferay-uirestore-entry-tag",
		"problem.summary=Changed the Usage of the liferay-ui:restore-entry Tag",
		"problem.tickets=LPS-54106",
	},
	service = FileMigrator.class
)
public class RestoreEntryTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findJSPTags("liferay-ui:restore-entry", null, null);
	}
}