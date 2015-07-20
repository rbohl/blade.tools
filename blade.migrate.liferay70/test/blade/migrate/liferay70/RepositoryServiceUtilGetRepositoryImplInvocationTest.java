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

public class RepositoryServiceUtilGetRepositoryImplInvocationTest {
	final File testFile = new File(
			"projects/filetests/RepositoryServiceUtilTest.java");

	final File jspFile = new File(
			"projects/filetests/RepositoryServiceUtilTest.jsp");

	RepositoryServiceUtilGetRepositoryImplInvocation component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new RepositoryServiceUtilGetRepositoryImplInvocation();
	}

	@Test
	public void repositoryServiceUtilAnalyzeTest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(1, results.size());
	}

	@Test
	public void repositoryServiceUtilTestTwice() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));
		results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(1, results.size());
	}

}
