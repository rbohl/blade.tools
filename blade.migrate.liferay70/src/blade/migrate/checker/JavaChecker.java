
package blade.migrate.checker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import blade.migrate.api.Problem;
import blade.migrate.core.CoreParser;
import blade.migrate.core.JavaClassVisitor;

public class JavaChecker extends BaseChecker
{

    private JavaClassVisitor classVisitor;
    private static CoreParser parser;

    public static CoreParser getParser()
    {
        if( parser == null )
        {
            parser = new CoreParser();
        }
        return parser;
    }

    public void setFile( File file )
    {
        super.setFile( file );

        try
        {
            classVisitor = getParser().visitFile( file );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    public Problem checkMethod( String name, List<String> paramentTypes, String description )
    {
        for( MethodDeclaration method : classVisitor.getMethods() )
        {
            if( name.equals( method.getName().toString() ) )
            {
                List parameters = method.parameters();

                if( parameters.size() == paramentTypes.size() )
                {
                    boolean flag = false;

                    for( int i = 0; i < parameters.size(); i++ )
                    {
                        SingleVariableDeclaration parameter = (SingleVariableDeclaration) parameters.get( i );

                        String expectParameterType = paramentTypes.get( i );

                        if( !expectParameterType.equals( parameter.getType().toString() ) )
                        {
                            break;
                        }

                        if( i == ( parameters.size() - 1 ) )
                        {
                            flag = true;
                        }
                    }

                    if( flag )
                    {
                        return new Problem( description );
                    }
                }
            }
        }

        return null;
    }

    public Problem checkImport( String importName, String description )
    {
        for( ImportDeclaration importDeclaration : classVisitor.getImports() )
        {
            if( importName.equals( importDeclaration.getName().toString() ) )
            {
                return new Problem( description );
            }
        }
        return null;
    }

}
