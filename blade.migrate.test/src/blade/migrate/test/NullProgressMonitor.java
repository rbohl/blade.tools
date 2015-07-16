package blade.migrate.test;

import blade.migrate.api.ProgressMonitor;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		Constants.SERVICE_RANKING + ":Integer=1000"
	}
)
public class NullProgressMonitor implements ProgressMonitor {

	@Override
	public void beginTask(String taskName, int totalWork) {
	}

	@Override
	public void done() {
	}

	@Override
	public void setTaskName(String taskName) {
	}

	@Override
	public void worked(int work) {
	}

}
