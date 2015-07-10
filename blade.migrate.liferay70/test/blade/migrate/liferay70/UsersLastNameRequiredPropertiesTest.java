package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class UsersLastNameRequiredPropertiesTest {
	final File file = new File(
			"projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/src/portal.properties");

	@Before
	public void beforeTest() {
		assertTrue(file.exists());
	}

	@Test
	public void usersLastNameRequiredPropertiesTest() throws Exception {
		List<Problem> problems = new UsersLastNameRequiredProperties().analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());

		Problem problem = problems.get(0);
		assertEquals(14, problem.lineNumber);
	}

	@Test
	public void portalPropertiesAnalyzeTest2() throws Exception {
		UsersLastNameRequiredProperties target = new UsersLastNameRequiredProperties();
		List<Problem> problems = target.analyzeFile(file);
		problems = target.analyzeFile(file);
		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
