
package blade.migrate.liferay70;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class AssetRendererIndexerAPIsTest
{
	final File testDir = new File( "projects/knowledge-base-portlet-6.2.x/" );

	@Before
	public void beforeTest()
	{
		assertTrue( testDir.exists() );
	}

    @Test
    public void assetRendererAPIsAnalyzeTest() throws Exception
    {
        List<Problem> problems = new AssetRendererAPIs().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }

    }

    @Test
    public void assetRendererAPIsAnalyzeTestTwice() throws Exception
    {
    	AssetRendererAPIs apis  = new AssetRendererAPIs();
    	List<Problem> problems = apis.analyze(testDir);
    	problems = apis.analyze(testDir);

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }

    }


    @Test
    public void IndexerAPIsAnalyzeTest() throws Exception
    {
        List<Problem> problems = new IndexerAPIs().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void IndexerAPIsAnalyzeTest2() throws Exception
    {
    	IndexerAPIs apis = new IndexerAPIs();

    	List<Problem> problems = apis.analyze( testDir );
    	problems = apis.analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }


    @Test
    public void ImportClazzAnalyzeTest() throws Exception
    {
        List<Problem> problems = new ImportClazz().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void ImportClazzAnalyzeTest2() throws Exception
    {
    	ImportClazz apis = new ImportClazz();

    	List<Problem> problems = apis.analyze( testDir );

    	apis.analyze( testDir );
        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void PortalPropertiesAnalyzeTest() throws Exception
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
    public void PortalPropertiesAnalyzeTest2() throws Exception
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
