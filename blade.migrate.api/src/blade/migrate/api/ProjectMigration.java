package blade.migrate.api;

import java.io.File;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface ProjectMigration {
	
	public void reportProblems(File projectDir);
	
}
