
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

public class DDMStructureUpdateStructureInvocationTest {
	final File testFile = new File(
			"projects/filetests/DDMStructureLocalServiceUtilTest.java");
	DDMStructureUpdateStructureInvocation component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new DDMStructureUpdateStructureInvocation();
	}

	@Test
	public void ddmSStructureAnalyzeTest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(1, results.size());
	}

	@Test
	public void ddmStructureAnalyzeTestTwice() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		results = component.searchJavaFile(testFile,
					new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(1, results.size());
	}
}
