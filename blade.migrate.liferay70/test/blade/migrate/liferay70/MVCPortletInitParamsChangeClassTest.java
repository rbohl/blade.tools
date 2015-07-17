package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import blade.migrate.api.Problem;

import java.io.File;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
public class MVCPortletChangeExtendsTest {

	@Before
	public void setUp() {
		assertTrue(testFile.exists());
		component = new MVCPortletChangeExtends();
	}

	@Test
	public void testMVCPortletChangeExtendsTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	private MVCPortletChangeExtends component;
	private final File testFile = new File(
		"projects/knowledge-base-portlet-6.2.x/docroot/"+
		"WEB-INF/src/com/liferay/knowledgebase/portlet/BaseKBPortlet.java");

}
