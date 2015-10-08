package blade.migrate.core;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class XMLFileCheckerTest {


	@Test
	public void offlineSupport() throws Exception {
		File file = new File("projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/service.xml");

		assertEquals(1, new XMLFileChecker(file).findTag("service-builder", null).size());
	}

	@Test
	public void elementContent() throws Exception {
		File file = new File("projects/knowledge-base-portlet-6.2.x/docroot/WEB-INF/service.xml");

		assertEquals(1, new XMLFileChecker(file).findTag("namespace", "KB").size());
	}

}
