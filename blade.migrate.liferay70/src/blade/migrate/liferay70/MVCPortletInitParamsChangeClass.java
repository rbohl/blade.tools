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
		"problem.title=copy-request-parameters init-param default value change",
		"problem.summary=The copy-request-parameters init parameter's default value is now set to true in all portlets that extend MVCPortlet.",
		"problem.tickets=LPS-54798",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-default-value-of-the-copy-request-parameters-init-parameter-for-mvc-portlets"
	},
	service = FileMigrator.class
)
public class MVCPortletInitParamsChangeClass  extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		return javaFileChecker.findSuperClass("MVCPortlet");
	}
}
