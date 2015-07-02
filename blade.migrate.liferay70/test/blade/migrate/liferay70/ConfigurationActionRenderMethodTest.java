package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class ConfigurationActionRenderMethodTest {

	final File configurationActionImplFile = new File( "projects/opensocial-portlet-6.2.x/docroot/WEB-INF/src/com/liferay/opensocial/gadget/action/ConfigurationActionImpl.java" );
	final File editConfigurationActionFile = new File( "projects/filetests/EditConfigurationAction.java" );
	ConfigurationActionRenderMethod component;

	@Before
	public void beforeTest() {
		assertTrue(configurationActionImplFile.exists());
		component = new ConfigurationActionRenderMethod();
	}

	@Test
	public void configurationActionImplFile() throws Exception {
		List<Problem> problems = component.analyzeFile(configurationActionImplFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void editConfigurationActionFile() throws Exception {
		List<Problem> problems = component.analyzeFile(editConfigurationActionFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
