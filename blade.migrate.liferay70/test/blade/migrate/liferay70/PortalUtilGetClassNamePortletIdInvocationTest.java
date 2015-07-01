
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class PortalUtilGetClassNamePortletIdInvocationTest
{
	final File testFile = new File( "projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/PortalUtilTest.java" );
	PortalUtilGetClassNamePortletIdInvocation apis;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		apis = new PortalUtilGetClassNamePortletIdInvocation();
	}

    @Test
    public void portalUtilGetClassNamePortletIdAnalyzeTest() throws Exception
    {
        List<Problem> problems = apis.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }

    @Test
    public void portalUtilGetClassNamePortletIdAnalyzeTestTwice() throws Exception
    {
    	List<Problem> problems = apis.analyzeFile(testFile);
    	problems = apis.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 1, problems.size() );
    }
}
