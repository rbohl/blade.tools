package blade.migrate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class AllJSPProblemsTest {

	private List<Problem> problems;

	@Before
	public void findProblems() throws Exception {
		if (problems == null) {
			ServiceReference<Migration> sr = context
					.getServiceReference(Migration.class);

			Migration m = context.getService(sr);

			problems = m
					.findProblems(new File(
							"../blade.migrate.liferay70/projects/jsptests/"));

			assertEquals(1, problems.size());
		}
	}

	@Test
	public void repositoryServiceUtilTest() throws Exception {
		boolean found = false;

		for (Problem problem : problems) {
			if (problem.file.getName().endsWith("RepositoryServiceUtilTest.jsp")) {
				if (problem.lineNumber == 9 && problem.startOffset == 104 && problem.endOffset == 171) {
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
