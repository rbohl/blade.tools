package blade.cli;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import aQute.lib.consoleapp.AbstractConsoleApp;
import aQute.lib.getopt.Description;
import blade.cli.cmds.CreateCommand;
import blade.cli.cmds.DeployCommand;
import blade.cli.cmds.OpenCommand;

/**
 * @author Gregory Amerson
 */
public class blade extends AbstractConsoleApp {

	public blade() throws UnsupportedEncodingException {
		super();
	}

	public blade(Object target) throws UnsupportedEncodingException {
		super(target);
	}

	public static void main(String[] args) throws Exception {
		new blade().run(args);
	}

	@Description("Creates a new Liferay module project.")
	public void _create(CreateOptions options) throws Exception {
		new CreateCommand(this, options);
	}

	@Description("Deploys a bundle to Liferay 7")
	public void _deploy(DeployOptions options) throws Exception {
		new DeployCommand(this, options);
	}

	@Description("Opens or imports a file or project in Liferay IDE")
	public void _open(OpenOptions options) throws Exception {
		new OpenCommand(this, options);
	}

	public PrintStream out() {
		return out;
	}
}
