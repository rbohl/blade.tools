package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

@Component(
	property = {
		"file.extension=java",
		"problem.title=StorageAdapter API Changes",
		"problem.summary=Removed Operations That Used the Fields Class from the StorageAdapter Interface",
		"problem.tickets=LPS-53021",
		"problem.type=java",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-operations-that-used-the-fields-class-from-the-storageadapter-interface"
	},
	service = FileMigrator.class
)
public class StorageAdapterCreateUpdateMethods extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final List<SearchResult> searchResults = new ArrayList<>();
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

		List<SearchResult> invocations = javaFileChecker.findMethodInvocations(
				null, "StorageEngineUtil", "create",
				new String[] { "long", "long", "Fields", "ServiceContext" });

		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations(null,
				"StorageEngineUtil", "update",
				new String[] { "long", "Fields", "boolean", "ServiceContext" });

		searchResults.addAll(invocations);

		invocations = javaFileChecker.findMethodInvocations(null,
				"StorageEngineUtil", "update",
				new String[] { "long", "Fields", "ServiceContext" });

		searchResults.addAll(invocations);

		return searchResults;
	}
}