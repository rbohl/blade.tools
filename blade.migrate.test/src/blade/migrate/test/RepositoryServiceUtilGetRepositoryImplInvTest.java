
package blade.migrate.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;

public class RepositoryServiceUtilGetRepositoryImplInvTest
{
		@Test
		public void testGetRepositoryImpl() throws Exception {
			ServiceReference<Migration> sr = context
				.getServiceReference(Migration.class);
			Migration m = context.getService(sr);
			List<Problem> problems = m
					.findProblems(new File(
							"../blade.migrate.liferay70/projects/filetests/RepositoryServiceUtilTest.java"));

			assertEquals(1, problems.size());
		}

		private final BundleContext context = FrameworkUtil.getBundle(
			this.getClass()).getBundleContext();

}
