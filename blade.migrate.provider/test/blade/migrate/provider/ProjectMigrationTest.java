package blade.migrate.provider;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import blade.migrate.api.Problem;

public class ProjectMigrationTest {

	@Test
	public void serviceTest() throws Exception {
		ProjectMigrationService pms = new ProjectMigrationService();
		pms.addProjectMigrator(new TestMigrator());

		List<Problem> problems = pms.reportProblems(new File("generated"));

		assertNotNull(problems);
		assertTrue(problems.size() > 0);
	}
}
