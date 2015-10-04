package blade.migrate.test;

import static org.junit.Assert.assertEquals;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ConsoleReporterCLITest {

	@Test
	public void reportLongFormatTest() throws Exception {
		String expectString =
				"   ________________________________________________________________________\n" +
				"   | Title| Summary    | Url               | Type| Ticket  | File    | Line|\n" +
				"   |=======================================================================|\n" +
				"1. | bar  | bar summary| http://liferay.com| jsp | LPS-867 | Bar.java| 20  |\n" +
				"2. | foo  | foo summary| http://liferay.com| java| LPS-5309| Foo.java| 10  |\n";


		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		ServiceReference<Reporter> sr = context
			.getServiceReference(Reporter.class);
		Reporter reporter = context.getService(sr);
		reporter.beginReporting(Migration.DETAIL_LONG, baos);
		reporter.report(new Problem(
				"foo", "http://liferay.com", "foo summary", "java", "LPS-5309", new File("Foo.java"), 10, 100, 110, null, null));
		reporter.report(new Problem(
				"bar", "http://liferay.com", "bar summary", "jsp", "LPS-867", new File("Bar.java"), 20, 200, 220, null, null));
		reporter.endReporting();

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	@Test
	public void reportShortFormatTest() throws Exception {
		String expectString =
				"   _____________________________\n" +
				"   | Title| Type| File    | Line|\n" +
				"   |============================|\n" +
				"1. | bar  | jsp | Bar.java| 20  |\n" +
				"2. | foo  | java| Foo.java| 10  |\n";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		ServiceReference<Reporter> sr = context
				.getServiceReference(Reporter.class);
		Reporter reporter = context.getService(sr);
		reporter.beginReporting(Migration.DETAIL_SHORT, baos);
		reporter.report(new Problem(
				"foo", "http://liferay.com", "foo summary", "java", "LPS-867", new File("Foo.java"), 10, 100, 110, null, null));
		reporter.report(new Problem(
				"bar", "http://liferay.com", "bar summary", "jsp", "LPS-5309", new File("Bar.java"), 20, 200, 220, null, null));
		reporter.endReporting();

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	private final BundleContext context = FrameworkUtil.getBundle(
		this.getClass()).getBundleContext();

}