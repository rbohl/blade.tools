package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class IndexerDoGetSummaryDeclTest {
	final File testFile = new File( "projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/src/com/liferay/knowledgebase/admin/util/AdminIndexer.java" );
	IndexerDoGetSummaryDecl component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new IndexerDoGetSummaryDecl();
	}

	@Test
	public void indexerDoGetSummaryDeclTest() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

	@Test
	public void indexerDoGetSummaryDeclTest2x() throws Exception {
		List<Problem> problems = component.analyzeFile(testFile);
		problems = component.analyzeFile(testFile);

		assertNotNull(problems);
		assertEquals(1, problems.size());
	}

}
