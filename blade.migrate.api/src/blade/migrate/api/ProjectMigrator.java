package blade.migrate.api;

import java.io.File;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface ProjectMigrator {

	public List<Problem> analyze(File projectDir);

}