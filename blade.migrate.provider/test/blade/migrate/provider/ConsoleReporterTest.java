package blade.migrate.provider;

import static org.junit.Assert.assertEquals;

import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
public class ConsoleReporterTest {

	@Test
	public void reportLongFormatTest() throws Exception {
		String expectString =
				"   ______________________________________________________________________________\n" +
				"   | Title| Summary| Url             | Type      | Ticket| File            | Line|\n" +
				"   |=============================================================================|\n" +
				"1. | test | summary| java/project/url| java      | 100   | file1.java      | 10  |\n" +
				"2. | test1| summary| java/project/url| properties| 101   | file2.properties| 12  |\n";

		Problem problem = new Problem();
		problem.title = "test";
		problem.summary = "summary";
		problem.url = "java/project/url";
		problem.ticket = "100";
		problem.type = "java";
		problem.file = new File("file1.java");
		problem.lineNumber = 10;

		Problem problem1 = new Problem();
		problem1.title = "test1";
		problem1.summary = "summary";
		problem1.url = "java/project/url";
		problem1.ticket = "101";
		problem1.type = "properties";
		problem1.file = new File("file2.properties");
		problem1.lineNumber = 12;

		List<Problem> problems = new ArrayList<>();
		problems.add(problem);
		problems.add(problem1);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		ConsoleReporter reporter = new ConsoleReporter();
		reporter.beginReporting(Reporter.FORMAT_LONG);

		for (Problem p : problems) {
			reporter.report(p);
		}

		reporter.endReporting();

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	@Test
	public void reportShortFormatTest() throws Exception {
		String expectString =
				"   ___________________________________________\n" +
				"   | Title| Type      | File            | Line|\n" +
				"   |==========================================|\n" +
				"1. | test | java      | file1.java      | 10  |\n" +
				"2. | test1| properties| file2.properties| 12  |\n";

		Problem problem = new Problem();
		problem.title = "test";
		problem.type = "java";
		problem.file = new File("file1.java");
		problem.lineNumber = 10;

		Problem problem1 = new Problem();
		problem1.title = "test1";
		problem1.type = "properties";
		problem1.file = new File("file2.properties");
		problem1.lineNumber = 12;

		List<Problem> problems = new ArrayList<>();
		problems.add(problem);
		problems.add(problem1);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		ConsoleReporter reporter = new ConsoleReporter();
		reporter.beginReporting(Reporter.FORMAT_SHORT);

		for (Problem p : problems) {
			reporter.report(p);
		}

		reporter.endReporting();

		baos = new ByteArrayOutputStream();
		printStream = new PrintStream(baos);
		System.setOut(printStream);

		reporter.beginReporting(Reporter.FORMAT_SHORT);

		for (Problem p : problems) {
			reporter.report(p);
		}

		reporter.endReporting();

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

}