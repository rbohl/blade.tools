package blade.migrate.test;

import static org.junit.Assert.assertEquals;

import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Collection;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class OSGiHtmlReporterTest {

	@Test
	public void reportLongFormatTest() throws Exception {
		String expectString =
"<!doctype html>\n\n"+

"<html class=\"no-js\" lang=\"\">\n" +
"    <head>\n" +
"        <meta charset=\"utf-8\">\n" +
"        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
"        <title></title>\n" +
"        <meta name=\"description\" content=\"\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"    </head>\n" +
"    <body>\n" +
"    	<table>\n" +
"    		<tr>\n" +
"    			<th>Title</th>\n" +
"    			<th>Summary</th>\n" +
"    			<th>Url</th>\n" +
"    			<th>Type</th>\n" +
"    			<th>Ticket</th>\n" +
"    			<th>File</th>\n" +
"    			<th>Line</th>\n" +
"    		</tr>\n" +
"	    	<tr>\n" +
"	    		<td>foo</td>\n" +
"	    		<td>foo summary</td>\n" +
"	    		<td>http://liferay.com</td>\n" +
"	    		<td>java</td>\n" +
"	    		<td>LPS-5309</td>\n" +
"	    		<td>Foo.java</td>\n" +
"	    		<td>10</td>\n" +
"	    	</tr>\n" +
"	    	<tr>\n" +
"	    		<td>bar</td>\n" +
"	    		<td>bar summary</td>\n" +
"	    		<td>http://liferay.com</td>\n" +
"	    		<td>jsp</td>\n" +
"	    		<td>LPS-867</td>\n" +
"	    		<td>Bar.java</td>\n" +
"	    		<td>20</td>\n" +
"	    	</tr>\n" +
"    	</table>\n" +
"    </body>\n" +
"</html>\n";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		Reporter reporter = null;

		try {
			Collection<ServiceReference<Reporter>> references = this.context.getServiceReferences(Reporter.class, "(format=html)");

			if( references.size() > 0 ) {
				ServiceReference<Reporter> sr = references.iterator().next();

				reporter = this.context.getService(sr);
			}
			else {
				ServiceReference<Reporter> sr = this.context.getServiceReference(Reporter.class);
				reporter = this.context.getService(sr);
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}

		reporter.beginReporting(Reporter.FORMAT_LONG, baos);
		reporter.report(new Problem(
				"foo", "http://liferay.com", "foo summary", "java", "LPS-5309", new File("Foo.java"), 10, 100, 110 ));
		reporter.report(new Problem(
				"bar", "http://liferay.com", "bar summary", "jsp", "LPS-867", new File("Bar.java"), 20, 200, 220 ));
		reporter.endReporting();

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	@Test
	public void reportShortFormatTest() throws Exception {
		String expectString =
"<!doctype html>\n\n"+

"<html class=\"no-js\" lang=\"\">\n" +
"    <head>\n" +
"        <meta charset=\"utf-8\">\n" +
"        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
"        <title></title>\n" +
"        <meta name=\"description\" content=\"\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"    </head>\n" +
"    <body>\n" +
"    	<table>\n" +
"    		<tr>\n" +
"    			<th>Title</th>\n" +
"    			<th>Type</th>\n" +
"    			<th>File</th>\n" +
"    			<th>Line</th>\n" +
"    		</tr>\n" +
"	    	<tr>\n" +
"	    		<td>foo</td>\n" +
"	    		<td>java</td>\n" +
"	    		<td>Foo.java</td>\n" +
"	    		<td>10</td>\n" +
"	    	</tr>\n" +
"	    	<tr>\n" +
"	    		<td>bar</td>\n" +
"	    		<td>jsp</td>\n" +
"	    		<td>Bar.java</td>\n" +
"	    		<td>20</td>\n" +
"	    	</tr>\n" +
"    	</table>\n" +
"    </body>\n" +
"</html>\n";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		Reporter reporter = null;

		try {
			Collection<ServiceReference<Reporter>> references = this.context.getServiceReferences(Reporter.class, "(format=html)");

			if( references.size() > 0 ) {
				ServiceReference<Reporter> sr = references.iterator().next();

				reporter = this.context.getService(sr);
			}
			else {
				ServiceReference<Reporter> sr = this.context.getServiceReference(Reporter.class);
				reporter = this.context.getService(sr);
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}

		reporter.beginReporting(Reporter.FORMAT_SHORT, baos);
		reporter.report(new Problem(
				"foo", "http://liferay.com", "foo summary", "java", "LPS-867", new File("Foo.java"), 10, 100, 110 ));
		reporter.report(new Problem(
				"bar", "http://liferay.com", "bar summary", "jsp", "LPS-5309", new File("Bar.java"), 20, 200, 220 ));
		reporter.endReporting();

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	private final BundleContext context = FrameworkUtil.getBundle(
		this.getClass()).getBundleContext();

}