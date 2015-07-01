
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DDMTemplateUpdateTemplateInvocationTest
{
	final File testFile = new File( "projects/filetests/DDMTemplateLocalServiceUtilTest.java" );
	DDMTemplateUpdateTemplateInvocation ddm;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		ddm = new DDMTemplateUpdateTemplateInvocation();
	}

    @Test
    public void DDMTemplateAnalyzeTest() throws Exception
    {
        List<Problem> problems = ddm.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }

    @Test
    public void DDMTemplateAnalyzeTestTwice() throws Exception
    {
    	List<Problem> problems = ddm.analyzeFile(testFile);
    	problems = ddm.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }
}
