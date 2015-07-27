package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JSPFileChecker;
import blade.migrate.core.JSPTagsFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=jsp,jspf",
		"problem.title=Removed the Tags that Start with portlet:icon-",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-the-tags-that-start-with-portleticon-",
		"problem.summary=Removed the Tags that Start with portlet:icon-",
		"problem.tickets=LPS-54620",
	},
	service = FileMigrator.class
)
public class PortletIconTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {
		String[] jspTags = new String[]{
			"liferay-portlet:icon-close",
			"liferay-portlet:icon-configuration",
			"liferay-portlet:icon-edit",
			"liferay-portlet:icon-edit-defaults",
			"liferay-portlet:icon-edit-guest",
			"liferay-portlet:icon-export-import",
			"liferay-portlet:icon-help",
			"liferay-portlet:icon-maximize",
			"liferay-portlet:icon-minimize",
			"liferay-portlet:icon-portlet-css",
			"liferay-portlet:icon-print",
			"liferay-portlet:icon-refresh",
			"liferay-portlet:icon-staging"
		};
		List<SearchResult> searchResults = new ArrayList<SearchResult>();

		for (String jspTag : jspTags) {
			List<SearchResult> searchResult = jspFileChecker.findJSPTags(jspTag, null);
			if( 0 != searchResult.size()){
				searchResults.addAll(searchResult);
			}
		}

		return searchResults;
	}
}