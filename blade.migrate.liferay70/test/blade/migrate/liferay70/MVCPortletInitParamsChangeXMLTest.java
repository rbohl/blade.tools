package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import blade.migrate.api.Problem;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Andy Wu
 */
public class MVCPortletInitParamsChangeXMLTest {

	@Before
	public void setUp() {
		assertTrue(testFile.exists());
		component = new MVCPortletInitParamsChangeXML();
	}

	@Test
	public void testMVCPortletChangeXMLTest() throws Exception {
		List<Problem> problems = component.analyze(testFile);

		assertNotNull(problems);
		assertEquals(5, problems.size());
	}

	private MVCPortletInitParamsChangeXML component;
	private final File testFile = new File(
		"projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/portlet.xml");

}
