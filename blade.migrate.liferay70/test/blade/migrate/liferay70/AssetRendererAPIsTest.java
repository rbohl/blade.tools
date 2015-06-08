
package blade.migrate.liferay70;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class AssetRendererAPIsTest
{
	final File testFile = new File( "projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/src/com/liferay/knowledgebase/admin/asset/KBArticleAssetRenderer.java" );



	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
	}

    @Test
    public void assetRendererAPIsAnalyzeTest() throws Exception
    {
        List<Problem> problems = new AssetRendererAPIs().analyzeFile( testFile );

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
    	List<Problem> problems = apis.analyzeFile(testFile);
    	problems = apis.analyzeFile(testFile);

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }

    }

}
