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
		"problem.title=Removed Trash Logic from DLAppHelperLocalService Methods",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-trash-logic-from-dlapphelperlocalservice-methods",
		"problem.summary=Removed Trash Logic from DLAppHelperLocalService Methods",
		"problem.tickets=LPS-47508",
	},
	service = FileMigrator.class
)
public class DLAppHelperLocalServiceUtilInvocation extends JavaFileMigrator {

    @Override
    protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
        final List<SearchResult> result = new ArrayList<SearchResult>();

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

        result.addAll(
            javaFileChecker.findMethodInvocations(
                null, "DLAppHelperLocalServiceUtil", "addFileEntry", null) );

        return result;
    }

}
