
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DDMStructureLocalServiceInvocationTest
{
	final File testFile = new File( "projects/filetests/DDMStructureLocalServiceAPITest.java" );
	DDMStructureLocalServiceAPI ddm;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		ddm = new DDMStructureLocalServiceAPI();
	}

    @Test
    public void DDMTemplateAnalyzeTest() throws Exception
    {
        List<Problem> problems = ddm.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );

        Problem problem = problems.get(0);

        assertEquals( 7, problem.lineNumber);
        assertEquals( 138, problem.startOffset);
        assertEquals( 264, problem.endOffset);
    }

}
