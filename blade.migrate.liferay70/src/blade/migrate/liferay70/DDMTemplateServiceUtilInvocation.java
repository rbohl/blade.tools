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
		"file.extensions=java",
		"problem.title=Added Required Parameter resourceClassNameId for DDM Template Search Operations",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#added-required-parameter-resourceclassnameid-for-ddm-template-search-operations",
		"problem.summary=Added Required Parameter resourceClassNameId for DDM Template Search Operations",
		"problem.tickets=LPS-52990",
	},
	service = FileMigrator.class
)
public class DDMTemplateServiceUtilInvocation extends JavaFileMigrator {

    @Override
    protected List<SearchResult> searchJavaFile(File file) {
        final List<SearchResult> result = new ArrayList<SearchResult>();

        final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DDMTemplateServiceUtil", "search", null));

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DDMTemplateServiceUtil", "searchCount", null) );

        return result;
    }

}
