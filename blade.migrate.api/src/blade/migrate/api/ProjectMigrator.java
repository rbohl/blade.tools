package blade.migrate.api;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface ProjectMigrator {
	
	public Object report();
	
}
