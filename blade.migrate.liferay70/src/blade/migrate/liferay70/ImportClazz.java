
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.checker.JavaChecker;

@Component
public class ImportClazz implements ProjectMigrator
{

    private static JavaChecker jc = new JavaChecker();

    public static final String VERSION_1_6 = "1.6";

    public static List<String> imports = new ArrayList<String>();

    @Override
    public List<Problem> analyze( File projectDir )
    {
        imports.add( "com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet" );
        imports.add( "javax.portlet.ActionRequest" );

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
                else if( currentFile.getName().endsWith( "java" ) )
                {
                    jc.setFile( currentFile );

                    for( String importtClass : imports )
                    {
                        StringBuffer sb = new StringBuffer();
                        sb.append( "Class Location:" );
                        sb.append( currentFile.getAbsolutePath() );
                        sb.append( "," );
                        sb.append( "import:" );
                        sb.append( importtClass );
                        sb.append( "." );

                        Problem problem = jc.checkImport( importtClass, sb.toString() );

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
