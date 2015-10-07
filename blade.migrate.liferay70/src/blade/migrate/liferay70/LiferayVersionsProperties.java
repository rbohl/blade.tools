package blade.migrate.liferay70;

import aQute.lib.io.IO;

import blade.migrate.api.AutoMigrateException;
import blade.migrate.api.AutoMigrator;
import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.PropertiesFileChecker;
import blade.migrate.core.PropertiesFileChecker.KeyInfo;
import blade.migrate.core.SearchResult;
import blade.migrate.core.WorkspaceHelper;
import blade.migrate.core.WorkspaceUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=properties",
		"problem.title=liferay-versions key in Liferay Plugin Packages Properties",
		"problem.summary=In order to deploy this project to 7.0 the liferay-versions property must be set to 7.0.0+",
		"problem.tickets=",
		"problem.section=",
		"auto.correct=property"
	},
	service = {
		FileMigrator.class,
		AutoMigrator.class
	}
)
public class LiferayVersionsProperties extends PropertiesFileMigrator implements AutoMigrator {

	private static final String PREFIX = "property:";

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
						String sectionHtml = _problemSummary;

						for (SearchResult searchResult : results) {
							searchResult.autoCorrectContext = PREFIX + "liferay-versions";

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

	@Override
	public void correctProblems(File file, List<Problem> problems) throws AutoMigrateException {
		try {
			String contents = new String(IO.read(file));

			IFile propertiesFile = WorkspaceUtil.getFileFromWorkspace(file, new WorkspaceHelper());

			for (Problem problem : problems) {
				if (problem.autoCorrectContext instanceof String) {
					final String propertyData = problem.autoCorrectContext;

					if (propertyData != null && propertyData.startsWith(PREFIX)) {
						final String propertyValue = propertyData.substring(PREFIX.length());

						contents = contents.replaceAll(propertyValue+".*", propertyValue + "=7.0.0+");
					}
				}
			}

			propertiesFile.setContents(new ByteArrayInputStream(contents.getBytes()), IResource.FORCE, null);

		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
	}

}
