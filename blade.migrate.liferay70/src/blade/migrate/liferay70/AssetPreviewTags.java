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
		"problem.title=Changed the Usage of Asset Preview",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-usage-of-asset-preview",
		"problem.summary=Changed the Usage of Asset Preview",
		"problem.tickets=LPS-53972",
	},
	service = FileMigrator.class
)
public class AssetPreviewTags extends JSPTagsFileMigrator {

	@Override
	protected List<SearchResult> searchJSPFile(File file,
			JSPFileChecker jspFileChecker) {

		return jspFileChecker.findMethodInvocations("AssetRenderer", null,
				"getPreviewPath", new String[] { "PortletRequest","PortletResponse" });
	}
}