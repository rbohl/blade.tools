
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class ContactNameExceptionImportTest {
	final File testFile = new File("projects/filetests/ContactNameExceptionImport.java");
	ContactNameExceptionImport component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new ContactNameExceptionImport();
	}

	@Test
	public void assetDLgetEntriesTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(3, problems.size());
	}

	@Test
	public void assetDLgetEntriesTestTwice() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);
		problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(3, problems.size());
	}
}
