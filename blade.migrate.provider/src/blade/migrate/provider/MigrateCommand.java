package blade.migrate.provider;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;

import java.io.File;
import java.util.List;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property = {
		CommandProcessor.COMMAND_SCOPE + "=blade",
		CommandProcessor.COMMAND_FUNCTION + "=migrate"
	},
	service = Object.class
)
public class MigrateCommand {

	public void migrate(File projectDir) {
		List<Problem> problems = projectMigrationService.findProblems(projectDir);
		projectMigrationService.reportProblems(problems, Migration.DETAIL_LONG, "console");
	}

	public void migrate(File projectDir, String format, File outputFile) {
		List<Problem> problems = projectMigrationService.findProblems(projectDir);
		projectMigrationService.reportProblems(problems, Migration.DETAIL_LONG, format, outputFile);
	}

	@Reference
	public void setProjectMigration(Migration projectMigration) {
		this.projectMigrationService = projectMigration;
	}

	private volatile Migration projectMigrationService;

}