
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

public class DDMStructureLocalServiceInvocationTest {
	final File testFile = new File( "projects/filetests/DDMStructureLocalServiceAPITest.java" );
	DDMStructureLocalServiceAPI component;

	@Before
	public void beforeTest() {
		assertTrue( testFile.exists() );
		component = new DDMStructureLocalServiceAPI();
	}

    @Test
    public void DDMTemplateAnalyzeTest() throws Exception {
    	List<SearchResult> results = component.searchJavaFile(testFile,
				new JavaFileChecker(testFile));

        assertNotNull(results);
        assertEquals(1, results.size());

        SearchResult problem = results.get(0);

        assertEquals(7, problem.startLine);
        assertEquals(138, problem.startOffset);
        assertEquals(264, problem.endOffset);
    }

}
