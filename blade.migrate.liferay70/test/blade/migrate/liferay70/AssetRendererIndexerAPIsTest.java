
package blade.migrate.liferay70;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import blade.migrate.api.Problem;

public class AssetRendererIndexerAPIsTest
{

    @Test
    public void assetRendererAPIsTest() throws Exception
    {
        String path = "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet";
        // String path =
        // "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet\\docroot\\WEB-INF\\src\\com\\liferay\\knowledgebase\\admin\\asset";
        File testDir = new File( path );

        assertTrue( testDir.exists() );

        List<Problem> problems = new AssetRendererAPIs().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void IndexerAPIsTest() throws Exception
    {
        String path = "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet";
        // String path =
        // "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet\\docroot\\WEB-INF\\src\\com\\liferay\\knowledgebase\\admin\\asset";
        File testDir = new File( path );

        assertTrue( testDir.exists() );

        List<Problem> problems = new IndexerAPIs().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void ImportClazzTest() throws Exception
    {
        String path = "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet";
        // String path =
        // "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet\\docroot\\WEB-INF\\src\\com\\liferay\\knowledgebase\\admin\\asset";
        File testDir = new File( path );

        assertTrue( testDir.exists() );

        List<Problem> problems = new ImportClazz().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

    @Test
    public void PortalPropertiesTest() throws Exception
    {
        String path = "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet";
        // String path =
        // "D:\\dev java\\github\\liferay\\liferay-plugins-trunk-git\\portlets\\knowledge-base-portlet\\docroot\\WEB-INF\\src\\com\\liferay\\knowledgebase\\admin\\asset";
        File testDir = new File( path );

        assertTrue( testDir.exists() );

        List<Problem> problems = new PortalProperties().analyze( testDir );

        assertNotNull( problems );
        assertTrue( problems.size() > 0 );

        for( Problem problem : problems )
        {
            System.out.println( problem.description );
        }
    }

}
