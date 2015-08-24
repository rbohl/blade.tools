package blade.migrate.api;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface MigrationListener {

	public void problemsFound(List<Problem> problems);

}
