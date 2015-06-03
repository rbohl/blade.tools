package blade.migrate.provider;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import blade.migrate.api.ProjectMigrator;

@Component(
	property =	{
		CommandProcessor.COMMAND_SCOPE + "=blade",
		CommandProcessor.COMMAND_FUNCTION + "=migrate"
	},
	service = Object.class
)
public class MigrateCommand {

	private Set<ProjectMigrator> projectMigrators = new HashSet<>();
	
	@Reference(
        cardinality = ReferenceCardinality.MULTIPLE,
        policy = ReferencePolicy.DYNAMIC,
        policyOption = ReferencePolicyOption.GREEDY,
        unbind = "removeProjectMigrator"
    )
	public void addProjectMigrator(ProjectMigrator projectMigrator) {
		projectMigrators.add(projectMigrator);
	}
	
	public void removeProjectMigrator(ProjectMigrator projectMigrator) {
		projectMigrators.remove(projectMigrator);
	}
	
	public void migrate(File projectDir) {
		for(ProjectMigrator pm : projectMigrators) {
			System.out.println(pm.report());
		}
	}
}