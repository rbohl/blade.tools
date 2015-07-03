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
			"file.extensions=java",
			"problem.summary=Added userId Parameter to Update Operations of DDMStructureLocalService and DDMTemplateLocalService",
			"problem.tickets=LPS-50939",
			"problem.title=DDMStructureLocalService add new param userId",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#added-userid-parameter-to-update-operations-of-ddmstructurelocalservice-and-ddmtemplatelocalservice",
		},
		service = FileMigrator.class
)
public class DDMStructureUpdateStructureInvocation extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

		return javaFileChecker.findMethodInvocations(null, "DDMStructureLocalServiceUtil", "updateStructure", null);
	}

}
