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

public class SocialNetworkingLegacyAPITest {
	final File testFile = new File(
			"projects/legacy-apis-ant-portlet/docroot/WEB-INF/src/com/liferay/GroupModelListener.java");
	final File testFile2 = new File(
			"projects/legacy-apis-ant-portlet/docroot/WEB-INF/src/com/liferay/MeetupsPortlet.java");
	SocialNetworkingLegacyAPI component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new SocialNetworkingLegacyAPI();
	}

	@Test
	public void socialNetworkingLegacyAPITest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(2, results.size());

		SearchResult problem = results.get(0);

		assertEquals(20, problem.startLine);
		assertEquals(762, problem.startOffset);
		assertEquals(824, problem.endOffset);

		problem = results.get(1);

		assertEquals(31, problem.startLine);
		assertEquals(1056, problem.startOffset);
		assertEquals(1119, problem.endOffset);

		results = component.searchJavaFile(testFile2,
				new JavaFileChecker(testFile2));

		assertNotNull(results);
		assertEquals(6, results.size());

		problem = results.get(0);

		assertEquals(24, problem.startLine);
		assertEquals(982, problem.startOffset);
		assertEquals(1047, problem.endOffset);

		problem = results.get(1);

		assertEquals(57, problem.startLine);
		assertEquals(1836, problem.startOffset);
		assertEquals(1899, problem.endOffset);

		problem = results.get(2);

		assertEquals(128, problem.startLine);
		assertEquals(4144, problem.startOffset);
		assertEquals(4439, problem.endOffset);

		problem = results.get(3);

		assertEquals(135, problem.startLine);
		assertEquals(4457, problem.startOffset);
		assertEquals(4775, problem.endOffset);

		problem = results.get(4);

		assertEquals(25, problem.startLine);
		assertEquals(1056, problem.startOffset);
		assertEquals(1128, problem.endOffset);

		problem = results.get(5);

		assertEquals(156, problem.startLine);
		assertEquals(5223, problem.startOffset);
		assertEquals(5348, problem.endOffset);

	}

}
