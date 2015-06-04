
package blade.migrate.checker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import blade.migrate.api.Problem;

public class PropertyChecker extends BaseChecker
{

    private Properties propertes;

    public void setFile( File file )
    {
        super.setFile( file );

        propertes = new Properties();

        try
        {
            propertes.load( new FileInputStream( file ) );
        }
        catch( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    public Problem checkProperty( String name, String description )
    {
        if( propertes.containsKey( name ) )
        {
            return new Problem( description );
        }

        return null;
    }

}
