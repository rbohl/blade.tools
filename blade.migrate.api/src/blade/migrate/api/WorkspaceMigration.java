package blade.migrate.api;

import java.util.List;

public interface WorkspaceMigration {

	public List<Problem> getStoredProblems(boolean includeResolved);
}
