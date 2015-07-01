
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DDMStructureUpdateStructureInvocationTest
{
	final File testFile = new File( "projects/filetests/DDMStructureLocalServiceUtilTest.java" );
	DDMStructureUpdateStructureInvocation ddm;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		ddm = new DDMStructureUpdateStructureInvocation();
	}

    @Test
    public void DDMStructureAnalyzeTest() throws Exception
    {
        List<Problem> problems = ddm.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }

    @Test
    public void DDMStructureAnalyzeTestTwice() throws Exception
    {
    	List<Problem> problems = ddm.analyzeFile(testFile);
    	problems = ddm.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }
}
