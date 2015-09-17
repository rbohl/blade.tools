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
		"problem.title=Removed mbMessages and fileEntryTuples Attributes from app-view-search-entry Tag",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-mbmessages-and-fileentrytuples-attributes-from-app-view-search-entry-tag",
		"problem.summary=Removed mbMessages and fileEntryTuples Attributes from app-view-search-entry Tag",
		"problem.tickets=LPS-55886",
	},
	service = FileMigrator.class
)
public class AppViewSearchEntryTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findJSPTags("liferay-ui:app-view-search-entry",
				new String[] { "mbMessages", "fileEntryTuples" }, null);
	}
}