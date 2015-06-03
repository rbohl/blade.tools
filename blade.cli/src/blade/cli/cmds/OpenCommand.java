package blade.cli.cmds;

import java.io.File;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import blade.cli.OpenOptions;
import blade.cli.blade;
import blade.cli.jmx.IDEConnector;

public class OpenCommand {

	final private blade blade;
	final private OpenOptions options;

	public OpenCommand(blade blade, OpenOptions options) throws Exception {
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
		File fileName = new File(options._().get(0));

		if (!fileName.exists()) {
			addError("open", "Unable to find specified file " +
				fileName.getAbsolutePath());
			return;
		}

		IDEConnector connector = null;

		try {
			connector = new IDEConnector();
		}
		catch (Exception e) {
			// ignore
		}

		if (connector == null) {
			addError("open", "Unable to connect to Eclipse/Liferay IDE instance.");
			return;
		}

		if (fileName.isDirectory()) {
			Object retval = connector.openDir(fileName);

			if (retval != null) {
				addError("open", retval.toString());
				return;
			}
		}
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
