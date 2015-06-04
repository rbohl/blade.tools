
package blade.migrate.core;

import java.io.*;
import java.util.Map;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class CoreParser
{

    public static boolean DEBUG;

    private String encoding = "UTF-8";

    public JavaClassVisitor visitFile( File file ) throws IOException
    {
        if( !file.exists() )
            new IllegalArgumentException( "File " + file.getAbsolutePath() + " doesn't exist" );

        String source = readFileToString( file, encoding );

        return visitString( source );
    }

    public static String readFileToString( File file, String encoding ) throws IOException
    {
        FileInputStream stream = new FileInputStream( file );

        String result = null;

        try
        {
            result = readInputStreamToString( stream, encoding );
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch( IOException e )
            {
            }
        }

        return result;
    }

    public static String readInputStreamToString( InputStream stream, String encoding ) throws IOException
    {

        Reader r = new BufferedReader( new InputStreamReader( stream, encoding ), 16384 );
        StringBuilder result = new StringBuilder( 16384 );
        char[] buffer = new char[16384];

        int len;
        while( ( len = r.read( buffer, 0, buffer.length ) ) >= 0 )
        {
            result.append( buffer, 0, len );
        }

        return result.toString();
    }

    @SuppressWarnings( "unchecked" )
    public JavaClassVisitor visitString( String source )
    {
        ASTParser parser = ASTParser.newParser( AST.JLS4 );

        Map<String, String> options = JavaCore.getOptions();

        JavaCore.setComplianceOptions( JavaCore.VERSION_1_6, options );

        parser.setCompilerOptions( options );

        parser.setResolveBindings( false );
        parser.setStatementsRecovery( false );
        parser.setBindingsRecovery( false );
        parser.setSource( source.toCharArray() );
        parser.setIgnoreMethodBodies( false );

        CompilationUnit ast = (CompilationUnit) parser.createAST( null );

        // AstVisitor extends org.eclipse.jdt.core.dom.ASTVisitor
        JavaClassVisitor visitor = new JavaClassVisitor();

        ast.accept( visitor );

        return visitor;
    }
}
