package blade.cli;

import aQute.lib.getopt.Description;
import aQute.lib.getopt.Options;

import blade.cli.cmds.Build;

import java.io.File;

public interface CreateOptions extends Options {

	@Description("The build type of project to create.  "
			+ "Valid values are maven or gradle. Default: gradle")
	public Build build();

	@Description("The directory where to create the new project.")
	public File dir();

}
