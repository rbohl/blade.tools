
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.checker.PropertyChecker;

@Component
public class PortalProperties implements ProjectMigrator
{

    private static PropertyChecker pc = new PropertyChecker();

    public static List<String> properties = new ArrayList<String>();

    @Override
    public List<Problem> analyze( File projectDir )
    {
        properties.add( "servlet.service.events.pre" );
        properties.add( "release.info.previous.build.number" );

        List<Problem> problems = new ArrayList<Problem>();

        checkFile( projectDir, problems );

        return problems;
    }

    public static void checkFile( File file, List<Problem> problems )
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
                else if( currentFile.getName().endsWith( "properties" ) )
                {
                    pc.setFile( currentFile );

                    for( String property : properties )
                    {
                        StringBuffer sb = new StringBuffer();
                        sb.append( "Properties File Location:" );
                        sb.append( currentFile.getAbsolutePath() );
                        sb.append( "," );
                        sb.append( "propertie:" );
                        sb.append( property );
                        sb.append( "." );

                        Problem problem = pc.checkProperty( property, sb.toString() );

                        if( problem != null )
                        {
                            problems.add( problem );
                        }
                    }
                }
            }
        }
    }

}
