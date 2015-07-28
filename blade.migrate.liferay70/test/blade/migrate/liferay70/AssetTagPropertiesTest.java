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

public class AssetTagPropertiesTest {

	final File testFile = new File("projects/filetests/MediaWikiImporter.java");
	final File testFile2 = new File("projects/filetests/AssetTagPropertiesTestFile.java");
	AssetTagProperties component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		assertTrue(testFile2.exists());
		component = new AssetTagProperties();
	}

	@Test
	public void assetTagPropertiesTest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile, new JavaFileChecker(testFile));

		assertNotNull(results);
		assertEquals(2, results.size());
	}
	@Test
	public void assetTagPropertiesAnotherTest() throws Exception {
		List<SearchResult> results = component.searchJavaFile(testFile2, new JavaFileChecker(testFile2));

		assertNotNull(results);
		assertEquals(4, results.size());
	}

}
