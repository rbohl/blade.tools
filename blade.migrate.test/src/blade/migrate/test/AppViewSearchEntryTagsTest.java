package blade.migrate.test;

import static org.junit.Assert.assertEquals;

import blade.migrate.api.Migration;
import blade.migrate.api.NullProgressMonitor;
import blade.migrate.api.Problem;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class AppViewSearchEntryTagsTest {

	@Test
	public void findProblems() throws Exception {
		ServiceReference<Migration> sr = context.getServiceReference(Migration.class);

		Migration m = context.getService(sr);

		List<Problem> problems = m.findProblems(new File("jsptests/app-view-search-entry/"), new NullProgressMonitor());

		assertEquals(2, problems.size());
	}

	private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

}