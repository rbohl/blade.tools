package blade.cli.util;

import blade.migrate.api.ProgressMonitor;

import org.osgi.service.component.annotations.Component;

@Component
public class ConsoleProgressMonitor implements ProgressMonitor {

	@Override
	public void beginTask(String taskName, int totalWork) {
		System.out.print(taskName + "\r");
	}

	@Override
	public void done() {
		System.out.println();
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void setTaskName(String taskName) {
		System.out.print("\033[K");
		System.out.print(taskName + "\r");
	}

	@Override
	public void worked(int work) {
	}

}
