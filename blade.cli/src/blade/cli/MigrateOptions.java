package blade.cli;

import aQute.lib.getopt.Arguments;
import aQute.lib.getopt.Description;
import aQute.lib.getopt.Options;

@Arguments(arg = "projectDir")
@Description("Migrates a plugin project to Liferay 7")
public interface MigrateOptions extends Options {

	@Description("Watches the deployed file for changes and will automatically redeploy")
	boolean report();

}