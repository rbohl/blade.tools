package blade.migrate.liferay70;

import java.io.File;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

@Component(
	property = {
		"file.extension=java",
		"problem.title=Moved the AssetPublisherUtil Class and Removed It from the Public API",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#moved-the-assetpublisherutil-class-and-removed-it-from-the-public-api",
		"problem.summary=Moved the AssetPublisherUtil Class and Removed It from the Public API",
		"problem.tickets=LPS-52744",
	},
	service = FileMigrator.class
)
public class AssetPublisherUtilInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

		return javaFileChecker.findMethodInvocations(null, "AssetPublisherUtil", "*", null);
	}
}
