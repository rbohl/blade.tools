package blade.migrate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;

public class JournalArticleTest {

	private List<Problem> problems;

	@Before
	public void findProblems() throws Exception {
		if (problems == null) {
			ServiceReference<Migration> sr = context
					.getServiceReference(Migration.class);

			Migration m = context.getService(sr);

			problems = m
					.findProblems(new File(
							"../blade.migrate.liferay70/projects/jsptests/journal-article/"));

			assertEquals(1, problems.size());
		}
	}

	@Test
	public void JournalArticleTagTest() throws Exception {
		boolean found = false;

		for (Problem problem : problems) {
			if (problem.file.getName().endsWith("JournalArticleTagDeclTest.jsp")) {
				if (problem.lineNumber == 4 && problem.startOffset == 167 && problem.endOffset == 327) {
					found = true;
				}
			}
		}

		if (!found) {
			fail();
		}
	}

	private final BundleContext context = FrameworkUtil.getBundle(
		this.getClass()).getBundleContext();

}