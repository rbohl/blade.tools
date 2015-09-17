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
		"problem.title=liferay-ui:logo-selector Tag Parameter Changes",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#the-liferay-uilogo-selector-tag-requires-parameter-changes",
		"problem.summary=Removed the editLogoURL of liferay-ui:logo-selector Tag",
		"problem.tickets=LPS-42645",
	},
	service = FileMigrator.class
)
public class LogoSelectorTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findJSPTags("liferay-ui:logo-selector",
				new String[] { "editLogoURL" } , null);
	}
}