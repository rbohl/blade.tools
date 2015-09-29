package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import blade.migrate.api.Problem;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EmailSignaturePropertiesTest {
	final File file = new File("projects/test-portlet/docroot/WEB-INF/src/portal.properties");
	EmailSignatureProperties component;

	@Before
	public void beforeTest() {
		assertTrue(file.exists());
		component = new EmailSignatureProperties();
		component.addPropertiesToSearch(component._properties);
	}

	@Test
	public void emailSignaturePropertiesAnalyzeTest() throws Exception {
		List<Problem> problems = component.analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void emailSignaturePropertiesAnalyzeTest2() throws Exception {
		List<Problem> problems = component.analyzeFile(file);
		problems = component.analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}
}
