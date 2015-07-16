package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class MVCPortletChangeXMLTest {

	final File testFile = new File("projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/portlet.xml");
	MVCPortletChangeXML component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new MVCPortletChangeXML();
	}

	@Test
	public void assetCopyRequestParametersXMLTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(5, problems.size());

	}

}
