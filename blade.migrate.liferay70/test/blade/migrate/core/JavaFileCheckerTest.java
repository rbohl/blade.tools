package blade.migrate.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class JavaFileCheckerTest {

	@Test
	public void checkStaticMethodInvocation() throws Exception {
		File file = new File( "projects/filetests/JavaFileCheckerTest.java" );
		JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		List<SearchResult> result = javaFileChecker.findMethodInvocation("String", "valueOf");

		assertNotNull(result.get(0));
		assertEquals( 14, result.get(0).startLine );
		assertEquals( 15, result.get(0).endLine );
		assertEquals( 218, result.get(0).startOffset );
		assertEquals( 240, result.get(0).endOffset );
	}

	@Test
	public void checkMethodInvocation() throws Exception {
		File file = new File( "projects/filetests/JavaFileCheckerTest.java" );
		JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		List<SearchResult> result = javaFileChecker.findMethodInvocation("foo", "bar");

		assertNotNull(result.get(0));
		assertEquals( 10, result.get(0).startLine );
		assertEquals( 11, result.get(0).endLine );
		assertEquals( 170, result.get(0).startOffset );
		assertEquals( 189, result.get(0).endOffset );
	}
}
