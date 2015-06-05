
package blade.migrate.liferay70;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class PortalPropertiesTest
{
	final File testDir = new File( "projects/knowledge-base-portlet-6.2.x/" );

	@Before
	public void beforeTest()
	{
		assertTrue( testDir.exists() );
	}

    @Test
    public void portalPropertiesAnalyzeTest() throws Exception
    {
        List<Problem> problems = new PortalProperties().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void portalPropertiesAnalyzeTest2() throws Exception
    {
        PortalProperties portalProperties = new PortalProperties();
		List<Problem> problems = portalProperties.analyze( testDir );
		problems = portalProperties.analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

}
