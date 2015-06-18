package blade.cli.cmds;

import java.io.File;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import blade.cli.MigrateOptions;
import blade.cli.blade;
import blade.migrate.api.Migration;
import blade.migrate.api.Reporter;

public class MigrateCommand {

	final private blade blade;
	final private MigrateOptions options;

	public MigrateCommand(blade blade, MigrateOptions options) throws Exception {
		this.blade = blade;
		this.options = options;

		List<String> args = options._();

		if (args.size() == 0) {
			// Default command
			printHelp();
		}
		else {
			execute();
		}
	}

	private void execute() throws Exception {
		File projectDir = new File(options._().get(0));

		if(!projectDir.exists()) {
			addError("migrate", "projectDir does not exist");
			return;
		}

		final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

		ServiceReference<Migration> sr = context.getServiceReference(Migration.class);
		Migration migrationService = context.getService(sr);

		migrationService.reportProblems(projectDir, Reporter.FORMAT_LONG);
	}

	private void printHelp() throws Exception {
		Formatter f = new Formatter();
		options._command().help(f, this);
		blade.out().println(f);
		f.close();
	}

	private void addError(String prefix, String msg) {
		blade.addErrors(prefix, Collections.singleton(msg));
	}
}
