
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DDMTemplateLocalServiceUtilInvocationTest
{
	final File testFile = new File( "projects/filetests/DDMTemplateServiceTest.java" );
	DDMTemplateLocalServiceUtilInvocation component;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		component = new DDMTemplateLocalServiceUtilInvocation();
	}

    @Test
    public void ddmTemplateLocalServiceTest() throws Exception
    {
        List<Problem> problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 6, problems.size() );
    }

    @Test
    public void ddmTemplateLocalServiceTestTwice() throws Exception
    {
    	List<Problem> problems = component.analyzeFile(testFile);
    	problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 6, problems.size() );
    }
}
