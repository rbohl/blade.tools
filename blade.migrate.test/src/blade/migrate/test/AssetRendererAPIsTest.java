package blade.migrate.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;

public class AssetRendererAPIsTest {
	private final BundleContext context = FrameworkUtil.getBundle(
			this.getClass()).getBundleContext();

	@Test
	public void testFileMigratorFilter() throws Exception {
		ServiceReference<Migration> sr = context
				.getServiceReference(Migration.class);
		Migration m = context.getService(sr);
		List<Problem> problems = m
				.findProblems(new File(
						"../blade.migrate.liferay70/projects/knowledge-base-portlet-6.2.x"));

		assertTrue(problems.size() > 0);
	}

}
