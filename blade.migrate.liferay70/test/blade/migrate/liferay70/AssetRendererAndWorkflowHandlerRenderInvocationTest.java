
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

public class AssetRendererAndWorkflowHandlerRenderInvocationTest {
	final File testFile = new File("projects/filetests/RenderTest.java");
	AssetRendererAndWorkflowHandlerRenderInvocation component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new AssetRendererAndWorkflowHandlerRenderInvocation();
	}

	@Test
	public void assetRenderTest() throws Exception {
		List<SearchResult> problems = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(problems);
		assertEquals(6, problems.size());
	}

	@Test
	public void assetRenderTestTwice() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(6, results.size());
	}
}
