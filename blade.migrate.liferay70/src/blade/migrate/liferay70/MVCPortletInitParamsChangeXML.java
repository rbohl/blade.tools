
package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.SearchResult;
import blade.migrate.core.XMLFileChecker;
import blade.migrate.core.XMLFileMigrator;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.osgi.service.component.annotations.Component;

import org.xml.sax.SAXException;

@Component(property = {
	"file.extensions=xml",
	"problem.summary=The copy-request-parameters init parameter's default value is now set to true in all portlets that extend MVCPortlet.",
	"problem.tickets=LPS-54798",
	"problem.title=Changed the Default Value of the copy-request-parameters Init Parameter for MVC Portlets",
	"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-default-value-of-the-copy-request-parameters-init-parameter-for-mvc-portlets"
}, service = FileMigrator.class)
public class MVCPortletChangeXML extends XMLFileMigrator {

	@Override
	protected List<SearchResult> searchXMLFile(File file) {

		// check if it is portlet.xml file

		if (!"portlet.xml".equals(file.getName()))
			return null;
		final XMLFileChecker xmlFileChecker = new XMLFileChecker(file);
		List<SearchResult> results = new ArrayList<>();

		try {
			results.addAll(xmlFileChecker.findTag(
				"portlet-class", "com.liferay.util.bridges.mvc.MVCPortlet"));
			results.addAll(xmlFileChecker.findTag(
				"name", "copy-request-parameters"));
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return results;
	}

}
