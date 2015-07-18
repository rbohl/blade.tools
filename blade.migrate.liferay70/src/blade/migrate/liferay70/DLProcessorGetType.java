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
		"file.extensions=java",
		"problem.title=Created a New getType Method That is Implemented in DLProcessor",
		"problem.summary=The DLProcessor interface has a new method getType().",
		"problem.tickets= LPS-53574",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#created-a-new-gettype-method-that-is-implemented-in-dlprocessor"
	},
	service = FileMigrator.class
)
public class DLProcessorGetType  extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return  javaFileChecker.findImplementsInterface("DLProcessor");
	}
}
