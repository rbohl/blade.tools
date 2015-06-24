package blade.migrate.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.SearchResult;

public class JavaFileCheckerTest {

	@Test
	public void checkStaticMethodInvocation() throws Exception {
		File file = new File( "projects/filetests/JavaFileCheckerTest.java" );
		JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		SearchResult result = javaFileChecker.findMethodInvocation("String", "valueOf");

		assertNotNull(result);
		assertEquals( 14, result.startLine );
		assertEquals( 15, result.endLine );
		assertEquals( 218, result.startOffset );
		assertEquals( 240, result.endOffset );
	}

	@Test
	public void checkMethodInvocation() throws Exception {
		File file = new File( "projects/filetests/JavaFileCheckerTest.java" );
		JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		SearchResult result = javaFileChecker.findMethodInvocation("foo", "bar");

		assertNotNull(result);
		assertEquals( 10, result.startLine );
		assertEquals( 11, result.endLine );
		assertEquals( 170, result.startOffset );
		assertEquals( 189, result.endOffset );
	}
}
