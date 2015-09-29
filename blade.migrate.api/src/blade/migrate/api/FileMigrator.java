package blade.migrate.api;

import java.io.File;
import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface FileMigrator {

	public List<Problem> analyzeFile(File file);

}