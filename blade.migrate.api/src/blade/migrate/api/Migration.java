package blade.migrate.api;

import java.io.File;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Migration {

	public List<Problem> findProblems(File projectDir);

	public void reportProblems(File projectDir);
}
