
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

public class AssetRendererAPIsTest
{
	private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

//    @Test
//    public void something() throws Exception {
//
//    	final FileMigrator[] migrator = new FileMigrator[1];
//
//    	new ServiceTracker<>(context, FileMigrator.class, new ServiceTrackerCustomizer<FileMigrator, FileMigrator>(){
//
//			@Override
//			public FileMigrator addingService(ServiceReference<FileMigrator> reference) {
//				FileMigrator m = context.getService(reference);
//				if(m.getClass().getSimpleName().contains("AssetRendererAPI")) {
//					migrator[0] = m;
//				}
//				return null;
//			}
//
//			@Override
//			public void modifiedService(ServiceReference<FileMigrator> reference, FileMigrator service) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void removedService(ServiceReference<FileMigrator> reference, FileMigrator service) {
//				// TODO Auto-generated method stub
//
//			}}).open();
//
//    	migrator[0].analyzeFile(new File("/tmp/foo"));
//    }

	@Test
	public void testFileMigratorFilter() throws Exception {
		ServiceReference<Migration> sr = context.getServiceReference(Migration.class);
		Migration m = context.getService(sr);
		List<Problem> problems = m.reportProblems(new File("../blade.migrate.liferay70/projects/knowledge-base-portlet-6.2.x"));

		assertTrue(problems.size()>0);
	}
}
