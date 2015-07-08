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
		"problem.title=Removed Trash Logic from DLAppHelperLocalService Methods",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-trash-logic-from-dlapphelperlocalservice-methods",
		"problem.summary=Removed Trash Logic from DLAppHelperLocalService Methods",
		"problem.tickets=LPS-47508",
	},
	service = FileMigrator.class
)
public class DLAppHelperLocalServiceUtilInvocation extends JavaFileMigrator {

    @Override
    protected List<SearchResult> searchJavaFile(File file) {
        final List<SearchResult> result = new ArrayList<SearchResult>();

        final JavaFileChecker javaFileChecker = new JavaFileChecker(file);

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DLAppHelperLocalServiceUtil", "deleteFileEntry", null));

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DLAppHelperLocalServiceUtil", "deleteFolder", null) );

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DLAppHelperLocalServiceUtil", "moveFileEntry", null));

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DLAppHelperLocalServiceUtil", "moveFolder", null) );

        return result;
    }

}
