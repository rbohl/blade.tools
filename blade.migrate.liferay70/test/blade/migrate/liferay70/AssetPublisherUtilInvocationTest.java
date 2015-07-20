package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AssetPublisherUtilInvocationTest {
	final File testFile = new File( "projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/AssetPubliserUtilTest.java" );
	AssetPublisherUtilInvocation component;

	@Before
	public void beforeTest() {
		assertTrue( testFile.exists() );
		component = new AssetPublisherUtilInvocation();
	}

    @Test
    public void assetRendererAPIsAnalyzeTest() throws Exception {
    	List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

        assertNotNull(results);
        assertEquals(2, results.size());
    }

}

