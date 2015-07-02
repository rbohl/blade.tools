
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class DLDLImplDLUtilgetEntriesInvocationTest
{
	final File testFile = new File( "projects/filetests/DLDLImplDLUtilgetEntriesTest.java" );
	DLDLImplDLUtilgetEntriesInvocation apis;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		apis = new DLDLImplDLUtilgetEntriesInvocation();
	}

    @Test
    public void assetDLDLImplDLUtilgetEntriesTest() throws Exception
    {
        List<Problem> problems = apis.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 5, problems.size() );
    }

    @Test
    public void assetDLDLImplDLUtilgetEntriesTestTwice() throws Exception
    {
    	List<Problem> problems = apis.analyzeFile(testFile);
    	problems = apis.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 5, problems.size() );
    }
}
