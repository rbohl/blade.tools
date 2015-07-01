package blade.migrate.test;

import static org.junit.Assert.assertEquals;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;

import java.io.File;

import java.util.List;

import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class PortalUtilGetClassNamePortletIdInvocationTest {

	@Test
	public void testFindProblems() throws Exception {
		ServiceReference<Migration> sr = context
			.getServiceReference(Migration.class);
		Migration m = context.getService(sr);
		List<Problem> problems = m
				.findProblems(new File(
						"../blade.migrate.liferay70/projects/test-ext/docroot/WEB-INF/ext-impl/src/com/liferay/test/PortalUtilTest.java"));

		assertEquals(1, problems.size());
	}

	private final BundleContext context = FrameworkUtil.getBundle(
		this.getClass()).getBundleContext();

}