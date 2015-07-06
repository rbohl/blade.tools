package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class ReservedUserIdExceptionTest {
	
	final File testFile = new File("projects/filetests/ReservedUserIdException.java");
	ReservedUserIdExceptionCatch component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new ReservedUserIdExceptionCatch();
	}

	@Test
	public void assetReservedUserIdExceptionCatchTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}
	
}
