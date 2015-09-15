package blade.cli;

import blade.cli.cmds.Build;
import blade.cli.cmds.IDE;
import blade.cli.cmds.Type;

import java.io.File;

import org.osgi.framework.Version;

import aQute.lib.getopt.Arguments;
import aQute.lib.getopt.Description;
import aQute.lib.getopt.Options;

@Arguments(arg = {"name", "[service]"})
@Description("Creates a new Liferay module project.")
public interface CreateOptions extends Options {

	@Description("If a class is generated in the project, " +
		"provide the name of the class to be generated." +
		" If not provided defaults to Project name.")
	public String classname();

	@Description("The build type of project to create.  "
			+ "Valid values are maven or gradle. Default: gradle")
	public Build build();

	@Description("The directory where to create the new project.")
	public File dir();

	@Description("The type of IDE metadata to create along side "
			+ "the new project.")
	public IDE ide();

	@Description("The type of Liferay module to create. "
            + "Valid values are service, jspportlet, or portlet.")
	public Type projectType();

	@Description("The version of Liferay to create the module for, "
			+ "by default its 7.0.0")
	public Version version();

}
