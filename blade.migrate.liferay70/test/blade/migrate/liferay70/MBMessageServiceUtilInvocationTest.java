
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class MBMessageServiceUtilInvocationTest
{
	final File testFile = new File( "projects/filetests/EditDiscussionAction.java" );
	MBMessageServiceUtilInvocation component;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		component = new MBMessageServiceUtilInvocation();
	}

    @Test
    public void dlAppHelperLocalServiceTest() throws Exception
    {
        List<Problem> problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 3, problems.size() );

        Problem problem = problems.get(0);

		assertEquals( 248, problem.lineNumber );
		assertEquals( 7830, problem.startOffset );
		assertEquals( 8058, problem.endOffset );
    }

    @Test
    public void dlAppHelperLocalServiceTestTwice() throws Exception
    {
    	List<Problem> problems = component.analyzeFile(testFile);
    	problems = component.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 3, problems.size() );
    }
}
