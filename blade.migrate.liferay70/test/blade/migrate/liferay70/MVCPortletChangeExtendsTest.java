package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class MVCPortletChangeExtendsTest {

	final File testFile = new File("projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/src/com/liferay/knowledgebase/portlet/BaseKBPortlet.java");
	MVCPortletChangeExtends component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new MVCPortletChangeExtends();
	}

	@Test
	public void mvcPortletChangeExtendsTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
