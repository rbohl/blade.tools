
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DDMTemplateUpdateTemplateDeclTest
{
	final File testFile = new File( "projects/filetests/DDMTemplateLocalServiceUtilTest.java" );
	DDMStructureUpdateStructureDecl ddm;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		ddm = new DDMStructureUpdateStructureDecl();
		ddm.methodType = "invocation";
		ddm.methodName = "updateTemplate";
		ddm.methodExpression = "DDMTemplateLocalServiceUtil";
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
