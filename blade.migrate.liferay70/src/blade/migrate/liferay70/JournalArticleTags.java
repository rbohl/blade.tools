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
		"problem.title=Removed the liferay-ui:journal-article tag",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-the-liferay-uijournal-article-tag",
		"problem.summary=Removed the liferay-ui:journal-article Tag",
		"problem.tickets=LPS-56383",
	},
	service = FileMigrator.class
)
public class JournalArticleTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findJSPTags("liferay-ui:journal-article", null , null);
	}
}