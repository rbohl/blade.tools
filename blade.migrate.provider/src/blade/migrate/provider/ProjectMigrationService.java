package blade.migrate.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import blade.migrate.api.Migration;
import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;

@Component(immediate = true)
public class ProjectMigrationService implements Migration {

	@Override
	public List<Problem> reportProblems(File projectDir) {

		final List<Problem> problems = new ArrayList<>();

		for(ProjectMigrator pm : projectMigrators) {
			try {
				problems.addAll(pm.analyze(projectDir));
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}

		return problems;
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
