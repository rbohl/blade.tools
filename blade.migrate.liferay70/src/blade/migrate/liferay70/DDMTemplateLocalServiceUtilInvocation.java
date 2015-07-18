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
		"file.extensions=java,jsp,jspf",
		"problem.title=Added Required Parameter resourceClassNameId for DDM Template Search Operations",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#added-required-parameter-resourceclassnameid-for-ddm-template-search-operations",
		"problem.summary=Added Required Parameter resourceClassNameId for DDM Template Search Operations",
		"problem.type=java,jsp",
		"problem.tickets=LPS-52990",
	},
	service = FileMigrator.class
)
public class DDMTemplateLocalServiceUtilInvocation extends JavaFileMigrator {

    @Override
    protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
        List<SearchResult> result = new ArrayList<SearchResult>();

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DDMTemplateLocalServiceUtil", "search", null));

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DDMTemplateLocalServiceUtil", "searchCount", null) );

        return result;
    }

}
