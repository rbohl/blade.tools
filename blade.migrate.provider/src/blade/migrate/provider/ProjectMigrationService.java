package blade.migrate.provider;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import blade.migrate.api.ProjectMigration;
import blade.migrate.api.ProjectMigrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

@Component(immediate = true)
public class ProjectMigrationService implements ProjectMigration {

	@Override
	public void reportProblems(File projectDir) {
		for(ProjectMigrator pm : projectMigrators) {
			System.out.println(pm.report());
		}
	}
	
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
	

	
	
	private Set<ProjectMigrator> projectMigrators = new HashSet<>();
	
	

}
