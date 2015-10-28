package blade.migrate.liferay70;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;

//@Component
public class SDKProjectMigrator implements ProjectMigrator {

	@Override
	public List<Problem> analyze(File projectDir) {
		final List<Problem> retval = new ArrayList<>();

		if (isSDKProjectDir(projectDir) &&
				(getSDKVersion(getSDKDir(projectDir)).compareTo(new Version("6.2")) >= 0) &&
				(getSDKVersion(getSDKDir(projectDir)).compareTo(new Version("7")) < 0)) {

			retval.add(new Problem(
					"Project not located in to 7.0 plugins SDK",
					"In order to migrate this project to 7.0, it needs to be copied to a 7.0 plugins SDK under the appropriate directory.",
					"project",
					"",
					projectDir, -1, -1, -1, null, null));
		}

		return retval;
	}

	private Version getSDKVersion(File sdkDir) {
		try (InputStream in = new FileInputStream(new File(sdkDir, "build.properties"))) {
			final Properties props = new Properties();
			props.load(in);

			return new Version((String) props.get("lp.version"));
		}
		catch( Exception e ) {
		}

		return new Version("0");
	}

	private boolean isSDKProjectDir(File projectDir) {
		final File sdkDir = getSDKDir(projectDir);
		final File buildProperties = new File(sdkDir, "build.properties");
        final File portletsBuildXml = new File(sdkDir, "portlets/build.xml");

		return sdkDir.exists() && buildProperties.exists() && portletsBuildXml.exists();
	}

	private File getSDKDir(File projectDir) {
		final IPath projectPath = new Path(projectDir.getAbsolutePath());
		final IPath sdkLocation = projectPath.removeLastSegments(2);
		final File sdkDir = sdkLocation.toFile();

		return sdkDir;
	}

}
