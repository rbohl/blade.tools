package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		List<SearchResult> results = component.searchJavaFile(unicodeLanguageImplFile,
				new JavaFileChecker(unicodeLanguageImplFile));

        assertNotNull(results);
        assertEquals(6, results.size());
	}

	@Test
	public void liferayPortletFile() throws Exception {
		List<SearchResult> results = component.searchJavaFile(liferayPortletFile,
				new JavaFileChecker(liferayPortletFile));

        assertNotNull(results);
        assertEquals(3, results.size());
	}

}
