package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import blade.migrate.api.Problem;

public class AssetRendererIndexerAPIsTest {

	@Test
	public void analyzeTest() throws Exception {
		File testDir = new File("files/" + AssetRendererIndexerAPIs.class.getSimpleName());
		
		assertTrue(testDir.exists());
		
		List<Problem> problems = new AssetRendererIndexerAPIs().analyze( testDir );
		
		assertNotNull(problems);
		assertTrue(problems.size() > 0);
	}
}
