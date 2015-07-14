
package blade.migrate.liferay70;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blade.migrate.api.Problem;

public class StorageAdapterCreateUpdateMethodsTest
{
	final File testFile = new File( "projects/filetests/StorageAdapterCreateUpdateMethodsTest.java" );
	StorageAdapterCreateUpdateMethods sac;

	@Before
	public void beforeTest()
	{
		assertTrue( testFile.exists() );
		sac = new StorageAdapterCreateUpdateMethods();
	}

    @Test
    public void StorageAdapterCreateUpdateTest() throws Exception
    {
        List<Problem> problems = sac.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 4, problems.size() );
    }

    @Test
    public void StorageAdapterCreateUpdateTestTwice() throws Exception
    {
        List<Problem> problems = sac.analyzeFile(testFile);
        problems = sac.analyzeFile(testFile);

        assertNotNull( problems );
        assertEquals( 4, problems.size() );
    }
}
