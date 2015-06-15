
package blade.migrate.liferay70;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class IndexerAPIsTest
{
	final File testFile = new File( "projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/src/com/liferay/knowledgebase/admin/util/AdminIndexer.java" );

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
	}

    @Test
    public void indexerAPIsAnalyzeTest() throws Exception
    {
        List<Problem> problems = new IndexerAPIs().analyzeFile( testFile );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

    }

    @Test
    public void indexerAPIsAnalyzeTest2() throws Exception
    {
    	IndexerAPIs apis = new IndexerAPIs();

    	List<Problem> problems = new IndexerAPIs().analyzeFile( testFile );
    	problems = apis.analyzeFile( testFile );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

    }

}
