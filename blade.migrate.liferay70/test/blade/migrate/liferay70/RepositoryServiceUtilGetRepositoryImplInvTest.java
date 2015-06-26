
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class RepositoryServiceUtilGetRepositoryImplInvTest
{
	final File testFile = new File( "projects/filetests/RepositoryServiceUtilTest.java" );
	RepositoryServiceUtilGetRepositoryImplInv rs;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		rs = new RepositoryServiceUtilGetRepositoryImplInv();
		rs.methodType = "invocation";
		rs.methodName = "getRepositoryImpl";
		rs.methodExpression = "RepositoryLocalServiceUtil";
	}

    @Test
    public void RepositoryServiceUtilAnalyzeTest() throws Exception
    {
        List<Problem> problems = rs.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }

    @Test
    public void RepositoryServiceUtilTestTwice() throws Exception
    {
    	List<Problem> problems = rs.analyzeFile(testFile);
    	problems = rs.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }
}
