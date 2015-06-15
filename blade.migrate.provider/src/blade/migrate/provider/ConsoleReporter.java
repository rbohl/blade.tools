package blade.migrate.provider;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

@Component
public class ConsoleReporter implements Reporter {

	@Override
	public void beginReporting() {
		System.out.println("=====");
	}

	@Override
	public void report(Problem problem) {
		System.out.println(problem.title);
	}

	@Override
	public void endReporting() {
		System.out.println("=====");
	}

}
