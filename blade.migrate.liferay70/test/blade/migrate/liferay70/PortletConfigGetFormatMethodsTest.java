package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class PortletConfigGetFormatMethodsTest {

	final File liferayPortletFile = new File( "projects/filetests/LiferayPortlet.java" );
	final File unicodeLanguageImplFile = new File( "projects/filetests/UnicodeLanguageImpl.java" );
	PortletConfigGetFormatMethods component;

	@Before
	public void beforeTest() {
		assertTrue(liferayPortletFile.exists());
		assertTrue(unicodeLanguageImplFile.exists());
		component = new PortletConfigGetFormatMethods();
	}

	@Test
	public void unicodeLanguageImplFile() throws Exception {
		List<Problem> problems = component.analyzeFile(unicodeLanguageImplFile);

		assertNotNull(problems);
		assertEquals(6, problems.size());
	}

	@Test
	public void liferayPortletFile() throws Exception {
		List<Problem> problems = component.analyzeFile(liferayPortletFile);

		assertNotNull(problems);
		assertEquals(3, problems.size());
	}

}
