package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DLProcessorGetTypeTest {

	final File testFile = new File("projects/filetests/DLPreviewableProcessor.java");
	DLProcessorGetType component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new DLProcessorGetType();
	}

	@Test
	public void assetDLProcessorGetTypeTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
