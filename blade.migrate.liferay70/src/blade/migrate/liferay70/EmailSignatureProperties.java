package blade.migrate.liferay70;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
	property = {
		"file.extensions=properties",
		"problem.title=Email Sigurature Properties",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#merged-configured-email-signature-field-into-the-body-of-email-messages-from-message-boards-and-wiki",
		"problem.summary=Merged Configured Email Signature Field into the Body of Email Messages from Message Boards and Wiki",
		"problem.tickets=LPS-44599",
	},
	service = FileMigrator.class
)
public class EmailSignatureProperties extends PropertiesFileMigrator {

	@Override
	protected void addPropertiesToSearch(List<String> properties) {
		properties.add("message.boards.email.message.added.signature");
		properties.add("message.boards.email.message.updated.signature");
		properties.add("wiki.email.page.added.signature");
		properties.add("wiki.email.page.updated.signature");
	}

}
