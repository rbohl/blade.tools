package blade.migrate.liferay70;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JSPFileChecker;
import blade.migrate.core.JSPTagsFileMigrator;
import blade.migrate.core.SearchResult;

@Component(
	property = {
		"file.extensions=jsp,jspf",
		"problem.title=Removed the liferay-ui:journal-article Tag",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-the-liferay-uijournal-article-tag",
		"problem.summary=Removed the liferay-ui:journal-article Tag",
		"problem.tickets=LPS-56383",
	},
	service = FileMigrator.class
)
public class JournalArticleTagDecl extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,JSPFileChecker jspFileChecker) {

		try {
			return jspFileChecker.findJSPTags("liferay-ui:journal-article");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}