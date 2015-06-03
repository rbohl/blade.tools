package blade.migrate.portal;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.ProjectMigrator;

@Component(immediate=true)
public class TestMigrator implements ProjectMigrator {

	@Override
	public Object report() {
		return "test1";
	}

}
