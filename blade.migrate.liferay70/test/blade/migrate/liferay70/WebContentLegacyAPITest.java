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

public class WebContentLegacyAPITest {
	final File testFile = new File(
			"projects/legacy-apis-ant-portlet/docroot/WEB-INF/src/com/liferay/LegacyAPIsAntPortlet.java");
	WebContentLegacyAPI component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new WebContentLegacyAPI();
	}

	@Test
	public void webContentLegacyAPITest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(5, results.size());

		SearchResult problem = results.get(0);

		assertEquals(20, problem.startLine);
		assertEquals(942, problem.startOffset);
		assertEquals(1004, problem.endOffset);

		problem = results.get(1);

		assertEquals(47, problem.startLine);
		assertEquals(1871, problem.startOffset);
		assertEquals(1904, problem.endOffset);

		problem = results.get(2);

		assertEquals(21, problem.startLine);
		assertEquals(1013, problem.startOffset);
		assertEquals(1079, problem.endOffset);

		problem = results.get(3);

		assertEquals(41, problem.startLine);
		assertEquals(1597, problem.startOffset);
		assertEquals(1655, problem.endOffset);

		problem = results.get(4);

		assertEquals(45, problem.startLine);
		assertEquals(1786, problem.startOffset);
		assertEquals(1829, problem.endOffset);
	}

}
