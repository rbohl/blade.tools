package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class AssetPublisherPropertiesPropertiesTest {
	final File file =
		new File("projects/test-portlet/docroot/WEB-INF/src/portal.properties");
	AssetPublisherProperties component;

	@Before
	public void beforeTest() {
		assertTrue(file.exists());
		component = new AssetPublisherProperties();
		component.addPropertiesToSearch(component._properties);
	}

	@Test
	public void AssetPublisherPropertiesAnalyzeTest() throws Exception {
		List<Problem> problems = component.analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void AssetPublisherAnalyzeTest2() throws Exception {
		List<Problem> problems = component.analyzeFile(file);
		problems = component.analyzeFile(file);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
