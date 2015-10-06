package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java",
		"problem.title=StorageAdapter API Changes",
		"problem.summary=Removed Operations That Used the Fields Class from the StorageAdapter Interface",
		"problem.tickets=LPS-53021",
		"problem.type=java",
		"problem.section=#removed-operations-that-used-the-fields-class-from-the-storageadapter-interface"
	},
	service = FileMigrator.class
)
public class StorageAdapterCreateUpdateMethods extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

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