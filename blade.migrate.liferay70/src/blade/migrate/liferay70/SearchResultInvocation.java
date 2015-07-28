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
			"problem.title=Removed getMbMessages , getFileEntryTuples and addMbMessage Methods from SearchResult Class",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-mbmessages-and-fileentrytuples-attributes-from-app-view-search-entry-tag",
			"problem.summary=Removed getMbMessages , getFileEntryTuples and addMbMessage Methods from SearchResult Class",
			"problem.tickets=LPS-55886",
		},
		service = FileMigrator.class
)
public class SearchResultInvocation extends JavaFileMigrator {

    @Override
    protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
        List<SearchResult> result = new ArrayList<SearchResult>();

        result.addAll(
            javaFileChecker.findMethodInvocations(
                "SearchResult", null, "getMBMessages", null));

        result.addAll(
            javaFileChecker.findMethodInvocations(
                 "SearchResult", null, "getFileEntryTuples", null) );

        result.addAll(
                javaFileChecker.findMethodInvocations(
                     "SearchResult", null, "addMBMessage", new String[]{"MBMessage"}) );

        return result;
    }

}
