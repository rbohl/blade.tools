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

public class ActionCommandImportsTest {

	final File sayHelloActionCommandFile = new File(
			"projects/actioncommand-demo-portlet/docroot/WEB-INF/src/com/liferay/demo/portlet/action/SayHelloActionCommand.java");
	final File sayHelloActionCommandFile2 = new File(
			"projects/actioncommand-demo-portlet/docroot/WEB-INF/src/com/liferay/demo/portlet/action/SayHelloActionCommand2.java");
	MVCPortletActionCommandImports component;

	@Before
	public void beforeTest() {
		assertTrue(sayHelloActionCommandFile.exists());
		component = new MVCPortletActionCommandImports();
	}

	@Test
	public void sayHelloActionCommandFile() throws Exception {
		List<SearchResult> results = component.searchJavaFile(sayHelloActionCommandFile,
				new JavaFileChecker(sayHelloActionCommandFile));

		assertNotNull(results);
		assertEquals(1, results.size());
	}

	@Test
	public void sayHelloActionCommandFile2() throws Exception {
		List<SearchResult> results = component.searchJavaFile(sayHelloActionCommandFile2,
				new JavaFileChecker(sayHelloActionCommandFile2));

		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void sayHelloActionCommandFile2x() throws Exception {
		List<SearchResult> results = component.searchJavaFile(sayHelloActionCommandFile,
				new JavaFileChecker(sayHelloActionCommandFile));

		component.searchJavaFile(sayHelloActionCommandFile,
				new JavaFileChecker(sayHelloActionCommandFile));

		assertNotNull(results);
		assertEquals(1, results.size());
	}
}
