
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.PropertiesFileChecker;
import blade.migrate.core.SearchResult;

@Component(
		property = { "file.extension=properties" }
	)
public class EmailSignatureProperties implements FileMigrator
{

    private final List<String> properties = new ArrayList<String>();

    public EmailSignatureProperties() {
    	properties.add("message.boards.email.message.added.signature");
    	properties.add("message.boards.email.message.updated.signature");
    	properties.add("wiki.email.page.added.signature");
    	properties.add("wiki.email.page.updated.signature");
	}

    @Override
    public List<Problem> analyzeFile( File file )
    {
    	PropertiesFileChecker propertiesFileChecker = new PropertiesFileChecker(file);
		final List<Problem> problems = new ArrayList<>();


		for (String key : properties) {
			SearchResult searchResult = propertiesFileChecker.findProperty(key);
			if(searchResult != null){
				problems.add(new Problem("", "https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#merged-configured-email-signature-field-into-the-body-of-email-messages-from-message-boards-and-wiki",
						"Merged Configured Email Signature Field into the Body of Email Messages from Message Boards and Wiki",
						"properties", "LPS-44599", file, -1));
			}
		}

		return problems;
    }

}
