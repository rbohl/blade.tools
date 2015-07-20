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

/**
 * @author Andy Wu
 */
public class MVCPortletInitParamsChangeClassTest {

	@Before
	public void setUp() {
		assertTrue(testFile.exists());
		component = new MVCPortletInitParamsChangeClass();
	}

	@Test
	public void testMVCPortletChangeExtendsTest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

        assertNotNull(results);
        assertEquals(1, results.size());
	}

	private MVCPortletInitParamsChangeClass component;
	private final File testFile = new File(
		"projects/knowledge-base-portlet-6.2.x/docroot/"+
		"WEB-INF/src/com/liferay/knowledgebase/portlet/BaseKBPortlet.java");

}
