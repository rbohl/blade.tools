package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.SearchResult;
import blade.migrate.core.XMLFileMigrator;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=xml",
		"problem.title=The build-service task must be executed to regenerate code",
		"problem.summary=In order to compile this project for 7.0, the build-service task must be executed once to regenerate all the code for version 7.0 of Liferay.",
		"problem.tickets=",
		"problem.section="
	},
	service = FileMigrator.class
)
public class RunServiceBuilderRequired extends XMLFileMigrator {

	@Override
	protected List<SearchResult> searchXMLFile(File file) {
		if (!"service.xml".equals(file.getName())) {
			return Collections.emptyList();
		}

//		final XMLFileChecker xmlFileChecker = new XMLFileChecker(file);

//		final List<SearchResult> results = new ArrayList<>();

//		results.addAll(xmlFileChecker.findTag("service-builder", null));

		return Collections.singletonList(new SearchResult(file, 0, 0, 1, 1, false));
	}

	@Override
	public List<Problem> analyze(File file) {
		final List<Problem> problems = super.analyze(file);

		for (Problem problem : problems) {
			problem.html = _problemSummary;
		}

		return problems;
	}

}
