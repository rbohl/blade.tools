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
		"problem.title=Renamed URI Attribute Used to Generate AUI Tag Library",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#renamed-uri-attribute-used-to-generate-aui-tag-library",
		"problem.summary=We should use the new AUI URI declaration:http://liferay.com/tld/aui",
		"problem.tickets=LPS-57809",
	},
	service = FileMigrator.class
)
public class AUILiferayTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findJSPTags("jsp:directive.taglib",
				new String[] { "uri" },new String[] { "http://alloy.liferay.com/tld/aui" });
	}
}