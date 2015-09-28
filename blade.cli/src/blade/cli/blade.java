package blade.cli;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import aQute.lib.consoleapp.AbstractConsoleApp;
import aQute.lib.getopt.Description;

import blade.cli.cmds.CreateCommand;
import blade.cli.cmds.DeployCommand;
import blade.cli.cmds.MigrateCommand;
import blade.cli.cmds.OpenCommand;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Gregory Amerson
 */
@Component(provide = Runnable.class, properties = { "main.thread=true"})
public class blade extends AbstractConsoleApp implements Runnable {

	private String[] args;

	public blade() throws UnsupportedEncodingException {
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

	public void _migrate(MigrateOptions options) throws Exception {
		new MigrateCommand(this, options);
	}

	@Description("Opens or imports a file or project in Liferay IDE")
	public void _open(OpenOptions options) throws Exception {
		new OpenCommand(this, options);
	}

	public PrintStream out() {
		return out;
	}

	@Override
	public void run() {
		try {
			new blade().run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Reference(target ="(launcher.arguments=*)")
	void args( Object object, Map<String, Object> map) {
		args = (String[])map.get("launcher.arguments");
	}

}