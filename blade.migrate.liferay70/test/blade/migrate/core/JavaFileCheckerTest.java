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
		List<SearchResult> searchResults = javaFileChecker.findMethodInvocations("String", "valueOf");

		assertNotNull(searchResults);

		SearchResult searchResult = searchResults.get(0);

		assertNotNull(searchResult);
		assertEquals( 14, searchResult.startLine );
		assertEquals( 15, searchResult.endLine );
		assertEquals( 218, searchResult.startOffset );
		assertEquals( 240, searchResult.endOffset );
	}

	@Test
	public void checkMethodInvocation() throws Exception {
		File file = new File( "projects/filetests/JavaFileCheckerTest.java" );
		JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		List<SearchResult> searchResults = javaFileChecker.findMethodInvocations("foo", "bar");

		assertNotNull(searchResults);

		assertEquals(3, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		assertNotNull(searchResult);
		assertEquals( 10, searchResult.startLine );
		assertEquals( 11, searchResult.endLine );
		assertEquals( 170, searchResult.startOffset );
		assertEquals( 189, searchResult.endOffset );
	}
}
