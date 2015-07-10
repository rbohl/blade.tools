
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DLAppHelperLocalServiceUtilInvocationTest
{
	final File testFile = new File( "projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/DLAppHelperLocalServiceUtilTest.java" );
	DLAppHelperLocalServiceUtilInvocation component;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		component = new DLAppHelperLocalServiceUtilInvocation();
	}

    @Test
    public void dlAppHelperLocalServiceTest() throws Exception
    {
        List<Problem> problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 5, problems.size() );
    }

    @Test
    public void dlAppHelperLocalServiceTestTwice() throws Exception
    {
    	List<Problem> problems = component.analyzeFile(testFile);
    	problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 5, problems.size() );
    }
}
