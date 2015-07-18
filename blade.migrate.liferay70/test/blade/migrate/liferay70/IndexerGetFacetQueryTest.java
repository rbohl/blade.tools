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

public class IndexerGetFacetQueryTest {

	final File assetEntriesFacetFile = new File( "projects/filetests/AssetEntriesFacet.java" );
	final File indexerWrapper = new File( "projects/filetests/IndexerWrapper.java" );
	IndexerGetFacetQuery component;

	@Before
	public void beforeTest() {
		assertTrue(assetEntriesFacetFile.exists());
		assertTrue(indexerWrapper.exists());
		component = new IndexerGetFacetQuery();
	}

	@Test
	public void assetEntriesFacetFile() throws Exception {
		List<SearchResult> problems = component.searchJavaFile(
				assetEntriesFacetFile,
				new JavaFileChecker(assetEntriesFacetFile));

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void indexerWrapperFile() throws Exception {
		List<SearchResult> problems = component.searchJavaFile(
				indexerWrapper,
				new JavaFileChecker(indexerWrapper));

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
