package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class AssetPublisherUtilInvocationTest
{
	final File testFile = new File( "projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/AssetPubliserUtilTest.java" );
	AssetPublisherUtilInvocation assetPub;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		assetPub = new AssetPublisherUtilInvocation();
	}

    @Test
    public void assetRendererAPIsAnalyzeTest() throws Exception
    {
        List<Problem> problems = assetPub.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 2, problems.size() );
    }

}

