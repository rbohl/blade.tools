package blade.cli.cmds;

import blade.cli.MigrateOptions;
import blade.cli.blade;
import blade.migrate.api.Migration;
import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class MigrateCommand {

	final private blade blade;
	final private MigrateOptions options;

	public MigrateCommand(blade blade, MigrateOptions options)
		throws Exception {

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

	private void addError(String prefix, String msg) {
		blade.addErrors(prefix, Collections.singleton(msg));
	}

	private void execute() throws Exception {
		File projectDir = new File(options._().get(0));

		if (!projectDir.exists()) {
			addError("migrate", "projectDir does not exist");
			return;
		}

		final BundleContext context = FrameworkUtil.getBundle(
			this.getClass()).getBundleContext();

		Format format = options.format();

		FileOutputStream fos = null;

		if (options._().size() > 1) {
			File file = new File(options._().get(1));

			if (!file.exists()) {
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
		}

		ServiceReference<Migration> migrationSR = context.getServiceReference(Migration.class);
		Migration migrationService = context.getService(migrationSR);

		List<Problem> problems = migrationService.findProblems(projectDir);

		String formatValue = format != null ? format.toString() : "";

		if (options.detailed()) {
			migrationService.reportProblems(problems, Reporter.FORMAT_LONG, formatValue, fos);
		}
		else {
			migrationService.reportProblems(problems, Reporter.FORMAT_SHORT, formatValue, fos);
		}
	}

	private void printHelp() throws Exception {
		Formatter f = new Formatter();
		options._command().help(f, this);
		blade.out().println(f);
		f.close();
	}

}