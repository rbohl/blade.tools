package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class RepositoryServiceUtilGetRepositoryImplInvocationTest {
	final File testFile = new File(
			"projects/filetests/RepositoryServiceUtilTest.java");
	RepositoryServiceUtilGetRepositoryImplInvocation component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new RepositoryServiceUtilGetRepositoryImplInvocation();
	}

	@Test
	public void repositoryServiceUtilAnalyzeTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void repositoryServiceUtilTestTwice() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);
		problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}
}
