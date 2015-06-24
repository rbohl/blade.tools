package blade.migrate.provider;

import blade.migrate.api.Migration;

import java.io.File;

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
		projectMigrationService.findProblems(projectDir);
	}

	@Reference
	public void setProjectMigration(Migration projectMigration) {
		this.projectMigrationService = projectMigration;
	}

	private volatile Migration projectMigrationService;

}