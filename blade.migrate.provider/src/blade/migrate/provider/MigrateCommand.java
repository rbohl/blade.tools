package blade.migrate.provider;

import java.io.File;
import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import blade.migrate.api.Migration;

@Component(
	property =	{
		CommandProcessor.COMMAND_SCOPE + "=blade",
		CommandProcessor.COMMAND_FUNCTION + "=migrate"
	},
	service = Object.class
)
public class MigrateCommand {

	private volatile Migration projectMigrationService;
	
	@Reference
	public void setProjectMigration(Migration projectMigration) {
		this.projectMigrationService = projectMigration;
	}
	
	
	public void migrate(File projectDir) {
		projectMigrationService.findProblems(projectDir);
	}
	
}