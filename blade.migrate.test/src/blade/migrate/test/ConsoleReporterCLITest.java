package blade.migrate.test;

import static org.junit.Assert.assertEquals;

import blade.migrate.api.Migration;
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
				"   ____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n" +
				"   | Title                    | Summary                                                                                   | Url                                                                                                                                                                                               | Type           | Ticket             | File                       | Line|\n" +
				"   |===================================================================================================================================================================================================================================================================================================================================================================================================|\n" +
				"1. |                          | Removed Portal Properties Used to Display Sections in Form Navigators                     | https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-portal-properties-used-to-display-sections-in-form-navigators                                  | java,properties| LPS-54903          | portal.properties          | -1  |\n" +
				"2. | AssetRenderer API Changes| Changed the AssetRenderer API to Include the PortletRequest and PortletResponse Parameters| https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters| java           | LPS-44639,LPS-44894| KBArticleAssetRenderer.java| 64  |\n" +
				"3. | Indexer API Changes      | Changed the Indexer API to Include the PortletRequest and PortletResponse Parameters      | https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters| java           | LPS-44639,LPS-44894| AdminIndexer.java          | 140 |\n";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		ServiceReference<Migration> sr = context
			.getServiceReference(Migration.class);
		Migration m = context.getService(sr);
		m.reportProblems(new File(
						"../blade.migrate.liferay70/projects/knowledge-base-portlet-6.2.x"), Reporter.FORMAT_LONG);

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	@Test
	public void reportShortFormatTest() throws Exception {
		String expectString =
				"   _______________________________________________________________________________\n" +
				"   | Title                    | Type           | File                       | Line|\n" +
				"   |==============================================================================|\n" +
				"1. |                          | java,properties| portal.properties          | -1  |\n" +
				"2. | AssetRenderer API Changes| java           | KBArticleAssetRenderer.java| 64  |\n" +
				"3. | Indexer API Changes      | java           | AdminIndexer.java          | 140 |\n";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		System.setOut(printStream);

		ServiceReference<Migration> sr = context
			.getServiceReference(Migration.class);
		Migration m = context.getService(sr);
		m.reportProblems(new File(
						"../blade.migrate.liferay70/projects/knowledge-base-portlet-6.2.x"), Reporter.FORMAT_SHORT);

		String realString = baos.toString().replace("\r", "");

		assertEquals(expectString, realString);
	}

	private final BundleContext context = FrameworkUtil.getBundle(
		this.getClass()).getBundleContext();

}