package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.MarkdownParser;
import blade.migrate.core.PropertiesFileChecker;
import blade.migrate.core.PropertiesFileChecker.KeyInfo;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=properties",
		"problem.title=liferay-versions key in Liferay Plugin Packages Properties",
		"problem.summary=In order to migrate ",
		"problem.tickets=",
		"problem.section=",
	},
	service = FileMigrator.class
)
public class LiferayVersionsProperties extends PropertiesFileMigrator {

	@Override
	protected void addPropertiesToSearch(List<String> _properties) {
	}

	@Override
	public List<Problem> analyze(File file) {
		final List<Problem> problems = new ArrayList<>();

		if (file.getName().equals("liferay-plugin-package.properties")) {
			PropertiesFileChecker propertiesFileChecker =
					new PropertiesFileChecker(file);

			List<KeyInfo> keys = propertiesFileChecker.getInfos("liferay-versions");

			if (keys != null && keys.size() > 0) {
				String versions = keys.get(0).value;

				if (!versions.matches(".*7\\.[0-9]\\.[0-9].*")) {
					List<SearchResult> results = propertiesFileChecker.findProperties("liferay-versions");

					if (results != null) {
						String sectionHtml = "<br/>";

						for (SearchResult searchResult : results) {
							problems.add(new Problem( _problemTitle, _problemSummary,
								_problemType, _problemTickets, file,
								searchResult.startLine, searchResult.startOffset,
								searchResult.endOffset, sectionHtml, searchResult.autoCorrectContext));
						}
					}
				}
			}
		}

		return problems;
	}

}
