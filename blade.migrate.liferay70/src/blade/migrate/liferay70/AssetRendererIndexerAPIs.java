package blade.migrate.liferay70;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;

@Component
public class AssetRendererIndexerAPIs implements ProjectMigrator {

	@Override
	public List<Problem> analyze(File projectDir) {
		return Collections.singletonList(new Problem());
	}

}
