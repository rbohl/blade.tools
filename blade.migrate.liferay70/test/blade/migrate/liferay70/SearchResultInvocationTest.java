package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.SearchResult;

public class SearchResultInvocationTest {
	final File testFile = new File( "projects/filetests/SearchResultTest.java" );
	SearchResultInvocation component;

	@Before
	public void beforeTest() {
		assertTrue( testFile.exists() );
		component = new SearchResultInvocation();
	}

	@Test
	public void dlFileEntryTypeLocalServiceTest() throws Exception {
		List<SearchResult> results =
			component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(3, results.size());
	}

	@Test
	public void dlFileEntryTypeLocalServiceTestTwice() throws Exception {
		List<SearchResult> results =
			component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		results =
			component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(3, results.size());
	}

}
