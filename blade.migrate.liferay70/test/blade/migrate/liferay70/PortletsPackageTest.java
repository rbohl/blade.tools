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

public class PortletsPackageTest {
	final File file = new File(
			"projects/filetests/PortletsPackageTest.java");
	PortletsPackage component;

	@Before
	public void beforeTest() {
		assertTrue(file.exists());
		component = new PortletsPackage();
	}

	@Test
	public void portalPropertiesAnalyzeTest() throws Exception {
		List<SearchResult> problems = component.searchJavaFile(file, new JavaFileChecker(file));

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void portalPropertiesAnalyzeTest2() throws Exception {
		List<SearchResult> problems = component.searchJavaFile(file, new JavaFileChecker(file));
		problems = component.searchJavaFile(file, new JavaFileChecker(file));

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
