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

public class DLFileEntryTypeDDMStructureInvocationTest {
	final File testFile = new File( "projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/DLFileEntryTypeLocalServiceUtilTest.java" );
	DLFileEntryTypeDDMStructureInvocation component;

	@Before
	public void beforeTest() {
		assertTrue( testFile.exists() );
		component = new DLFileEntryTypeDDMStructureInvocation();
	}

	@Test
	public void dlFileEntryTypeLocalServiceTest() throws Exception {
		List<SearchResult> results =
			component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(10, results.size());
	}

	@Test
	public void dlFileEntryTypeLocalServiceTestTwice() throws Exception {
		List<SearchResult> results =
			component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		results =
			component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(10, results.size());
	}

}
