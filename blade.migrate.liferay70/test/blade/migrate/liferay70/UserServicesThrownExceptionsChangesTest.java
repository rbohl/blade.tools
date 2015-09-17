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

public class UserServicesThrownExceptionsChangesTest {
	final File testFile = new File(
			"projects/filetests/UserServicesThrownExceptionsChangesTest.java");
	UserServicesThrownExceptionsChanges component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new UserServicesThrownExceptionsChanges();
	}

	@Test
	public void userServicesThrownExceptionsChanges() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void userServicesThrownExceptionsChangesTestTwice() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(2, results.size());
	}
}
