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

public class LockLegacyAPITest {
	final File testFile = new File(
			"projects/legacy-apis-ant-portlet/docroot/WEB-INF/src/com/liferay/LockProtectedAction.java");
	LockLegacyAPI component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new LockLegacyAPI();
	}

	@Test
	public void lockLegacyAPITest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(4, results.size());

		SearchResult problem = results.get(0);

		assertEquals(22, problem.startLine);
		assertEquals(872, problem.startOffset);
		assertEquals(919, problem.endOffset);

		problem = results.get(1);

		assertEquals(46, problem.startLine);
		assertEquals(1375, problem.startOffset);
		assertEquals(1438, problem.endOffset);

		problem = results.get(2);

		assertEquals(62, problem.startLine);
		assertEquals(1686, problem.startOffset);
		assertEquals(1745, problem.endOffset);

		problem = results.get(3);

		assertEquals(73, problem.startLine);
		assertEquals(1899, problem.startOffset);
		assertEquals(1971, problem.endOffset);

	}

}
