
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.SearchResult;

@Component(
		property = { "file.extension=java" }
	)
public class IndexerAPIs implements FileMigrator
{
    private static final String methodName = "doGetSummary";
    private final List<String> oldParameters = new ArrayList<String>();
    private final List<String> newParameters = new ArrayList<String>();

    public IndexerAPIs() {
    	oldParameters.add( "Document" );
    	oldParameters.add( "Locale" );
    	oldParameters.add( "String" );
    	oldParameters.add( "PortletURL" );

    	newParameters.add( "Document" );
    	newParameters.add( "Locale" );
    	newParameters.add( "String" );
    	newParameters.add( "PortletRequest" );
    	newParameters.add( "PortletResponse" );
	}

	@Override
	public List<Problem> analyzeFile(File file) {
		JavaFileChecker javaFileChecker = new JavaFileChecker(file);
        final List<Problem> problems = new ArrayList<>();

        SearchResult methodResult = javaFileChecker.findMethod(methodName, oldParameters.toArray(new String[0]));
        if(methodResult != null){
        	problems.add(new Problem("Indexer API Changes","https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters",
        			"Changed the Indexer API to Include the PortletRequest and PortletResponse Parameters",
        			"java","LPS-44639,LPS-44894",file,methodResult.startLine));
        }

        return problems;
	}

}
