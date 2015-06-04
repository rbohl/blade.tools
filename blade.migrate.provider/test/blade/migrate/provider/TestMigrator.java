package blade.migrate.provider;

import java.io.File;
import java.util.Collections;
import java.util.List;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;

public class TestMigrator implements ProjectMigrator {

	@Override
	public List<Problem> analyze(File projectDir) {
		return Collections.singletonList(new Problem());
	}

}
