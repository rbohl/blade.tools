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

public class DLAppHelperLocalServiceUtilInvocationTest {
	final File testFile = new File( "projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/DLAppHelperLocalServiceUtilTest.java" );
	DLAppHelperLocalServiceUtilInvocation component;

	@Before
	public void beforeTest() {
		assertTrue( testFile.exists() );
		component = new DLAppHelperLocalServiceUtilInvocation();
	}

    @Test
    public void dlAppHelperLocalServiceTest() throws Exception {
    	List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(5, results.size());
    }

    @Test
    public void dlAppHelperLocalServiceTestTwice() throws Exception {
    	List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

    	results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(5, results.size());
    }
}
