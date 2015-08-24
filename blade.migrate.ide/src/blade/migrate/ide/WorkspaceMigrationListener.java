package blade.migrate.ide;

import blade.migrate.api.MigrationListener;
import blade.migrate.api.Problem;

import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component
public class WorkspaceMigrationListener implements MigrationListener {

	@Override
	public void problemsFound(List<Problem> problems) {
		for(Problem p:problems)
			System.out.println(p);
	}

}
