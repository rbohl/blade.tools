package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class EmailSignaturePropertiesTest {
	final File file = new File("projects/test-portlet/docroot/WEB-INF/src/portal.properties");

	@Before
	public void beforeTest() {
		assertTrue(file.exists());
	}

	@Test
	public void emailSignaturePropertiesAnalyzeTest() throws Exception {
		List<Problem> problems = new EmailSignatureProperties().analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void emailSignaturePropertiesAnalyzeTest2() throws Exception {
		EmailSignatureProperties emailSignatureProperties = new EmailSignatureProperties();
		List<Problem> problems = emailSignatureProperties.analyzeFile(file);
		problems = emailSignatureProperties.analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}
}
