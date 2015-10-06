package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java,jsp,jspf",
		"problem.title=Moved the AssetPublisherUtil Class and Removed It from the Public API",
		"problem.section=#moved-the-assetpublisherutil-class-and-removed-it-from-the-public-api",
		"problem.summary=Moved the AssetPublisherUtil Class and Removed It from the Public API",
		"problem.tickets=LPS-52744",
	},
	service = FileMigrator.class
)
public class AssetPublisherUtilInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findMethodInvocations(null, "AssetPublisherUtil", "*", null);
	}
}
