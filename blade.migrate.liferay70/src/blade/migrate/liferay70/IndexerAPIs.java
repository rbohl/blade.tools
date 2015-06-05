
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.checker.JavaChecker;

@Component
public class IndexerAPIs implements ProjectMigrator
{

    private static JavaChecker jc = new JavaChecker();

    private static final String name = "doGetSummary";
    private List<String> oldParameters = new ArrayList<String>();
    private List<String> newParameters = new ArrayList<String>();

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
    public List<Problem> analyze( File projectDir )
    {
        List<Problem> problems = new ArrayList<Problem>();

        checkFile( projectDir, problems );

        return problems;
    }

    public void checkFile( File file, List<Problem> problems )
    {
        if( file.isDirectory() )
        {
            File[] files = file.listFiles();

            for( File currentFile : files )
            {
                if( currentFile.isDirectory() )
                {
                    checkFile( currentFile, problems );
                }
                else if( currentFile.getName().endsWith( "java" ) )
                {
                    jc.setFile( currentFile );

                    StringBuffer sb = new StringBuffer();
                    sb.append( "Class Location:" );
                    sb.append( currentFile.getAbsolutePath() );
                    sb.append( "," );
                    sb.append( "methond:" );
                    sb.append( name );
                    sb.append( "." );

                    Problem problem = jc.checkMethod( name, oldParameters, sb.toString() );

                    if( problem != null )
                    {
                        problems.add( problem );
                    }
                }
            }
        }
    }

}
