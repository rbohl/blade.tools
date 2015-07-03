package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class ActionCommandImportsTest {

	final File sayHelloActionCommandFile = new File(
			"projects/actioncommand-demo-portlet/docroot/WEB-INF/src/com/liferay/demo/portlet/action/SayHelloActionCommand.java");
	final File sayHelloActionCommandFile2 = new File(
			"projects/actioncommand-demo-portlet/docroot/WEB-INF/src/com/liferay/demo/portlet/action/SayHelloActionCommand2.java");
	ActionCommandImports component;

	@Before
	public void beforeTest() {
		assertTrue(sayHelloActionCommandFile.exists());
		component = new ActionCommandImports();
	}

	@Test
	public void sayHelloActionCommandFile() throws Exception {
		List<Problem> problems = component.analyzeFile(sayHelloActionCommandFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void sayHelloActionCommandFile2() throws Exception {
		List<Problem> problems = component.analyzeFile(sayHelloActionCommandFile2);

		assertNotNull(problems);
		assertEquals(2, problems.size());
	}

	@Test
	public void sayHelloActionCommandFile2x() throws Exception {
		List<Problem> problems = component.analyzeFile(sayHelloActionCommandFile);
		problems = component.analyzeFile(sayHelloActionCommandFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}
}
