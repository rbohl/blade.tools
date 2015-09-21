package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import blade.migrate.api.Problem;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SDKProjectMigratorTest {

	final File sdkProject = new File("projects/sdk-project/portlets/test-portlet");
	final File sdkProjectBadLocation = new File("projects/sdk-project-bad-location");
	final File sdkProjectBadVersion61 = new File("projects/sdk-project-bad-version-61/portlets/test-portlet");
	final File sdkProjectBadVersion70 = new File("projects/sdk-project-bad-version-70/portlets/test-portlet");

	SDKProjectMigrator component;

	@Before
	public void beforeTest() {
		assertTrue(sdkProject.exists());
		assertTrue(sdkProjectBadLocation.exists());
		assertTrue(sdkProjectBadVersion61.exists());
		assertTrue(sdkProjectBadVersion70.exists());

		component = new SDKProjectMigrator();
	}

	@Test
	public void sdkProject() throws Exception {
		List<Problem> results = component.analyze(sdkProject);

		assertNotNull(results);
		assertEquals(1, results.size());
	}

	@Test
	public void sdkProjectBadLocation() throws Exception {
		List<Problem> results = component.analyze(sdkProjectBadLocation);

		assertNotNull(results);
		assertEquals(0, results.size());
	}

	@Test
	public void sdkProjectBadVersion61() throws Exception {
		List<Problem> results = component.analyze(sdkProjectBadVersion61);

		assertNotNull(results);
		assertEquals(0, results.size());
	}

	@Test
	public void sdkProjectBadVersion70() throws Exception {
		List<Problem> results = component.analyze(sdkProjectBadVersion70);

		assertNotNull(results);
		assertEquals(0, results.size());
	}
}
