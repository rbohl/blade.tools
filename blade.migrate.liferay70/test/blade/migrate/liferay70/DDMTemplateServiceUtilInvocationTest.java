
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DDMTemplateServiceUtilInvocationTest
{
	final File testFile = new File( "projects/filetests/DDMTemplateServiceSoap.java" );
	DDMTemplateServiceUtilInvocation component;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		component = new DDMTemplateServiceUtilInvocation();
	}

    @Test
    public void ddmTemplateServiceTest() throws Exception
    {
        List<Problem> problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 8, problems.size() );
    }

    @Test
    public void ddmTemplateServiceTestTwice() throws Exception
    {
    	List<Problem> problems = component.analyzeFile(testFile);
    	problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 8, problems.size() );
    }
}
