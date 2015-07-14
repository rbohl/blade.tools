package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class ConvertProcessExtendsTest {

	final File testFile = new File("projects/filetests/ConvertDatabase.java");
	ConvertProcessExtends component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new ConvertProcessExtends();
	}

	@Test
	public void assertConvertProcessExtendsTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
