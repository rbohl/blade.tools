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
		"problem.summary=The getEntries method was no longer used, and contained hardcoded references to classes that will be moved into OSGi bundles.",
		"problem.tickets=LPS-56247",
		"problem.title=Removed Method getEntries from DL, DLImpl, and DLUtil Classes", 
		"problem.type=java,jsp",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-method-getentries-from-dl-dlimpl-and-dlutil-classes"
	},
	service = FileMigrator.class
)
public class DLDLImplDLUtilgetEntriesInvocation extends JavaFileMigrator {
	
	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);	
		
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<SearchResult> dlResultsType = javaFileChecker.findMethodInvocations("DL", null, "getEntries");
		List<SearchResult> dlResultsStatic = javaFileChecker.findMethodInvocations(null, "DL", "getEntries");
		List<SearchResult> dlImplresultsType = javaFileChecker.findMethodInvocations("DLImpl", null, "getEntries");
		List<SearchResult> dlImplresultsStatic = javaFileChecker.findMethodInvocations(null, "DLImpl", "getEntries");		
		List<SearchResult> dlUtilresultsType = javaFileChecker.findMethodInvocations("DLUtil", null, "getEntries");
		List<SearchResult> dlUtilresultsStatic = javaFileChecker.findMethodInvocations(null, "DLUtil", "getEntries");		
		results.addAll(dlResultsType);
		results.addAll(dlResultsStatic);
		results.addAll(dlImplresultsType);
		results.addAll(dlImplresultsStatic);
		results.addAll(dlUtilresultsType);
		results.addAll(dlUtilresultsStatic);
		
		return results;
	}
	
}