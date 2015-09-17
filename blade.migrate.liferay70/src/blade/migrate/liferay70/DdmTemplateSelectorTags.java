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
		"problem.title=Changed Usage of the liferay-ui:ddm-template-selector Tag",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-usage-of-the-liferay-uiddm-template-selector-tag",
		"problem.summary=The attribute classNameId of the liferay-ui:ddm-template-selector taglib tag has been renamed className",
		"problem.tickets=LPS-53790",
	},
	service = FileMigrator.class
)
public class DdmTemplateSelectorTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findJSPTags("liferay-ui:ddm-template-selector", new String[]{"classNameId"}, null);
	}
}