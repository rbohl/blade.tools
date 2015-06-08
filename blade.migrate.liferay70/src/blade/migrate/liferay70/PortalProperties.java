
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.checker.PropertyChecker;
import blade.migrate.core.FileHelper;

@Component
public class PortalProperties implements ProjectMigrator
{

    private static PropertyChecker pc = new PropertyChecker();

    private final List<String> properties = new ArrayList<String>();
    private final FileHelper fileHelper = new FileHelper();

    public PortalProperties() {
    	properties.add( "servlet.service.events.pre" );
        properties.add( "release.info.previous.build.number" );
	}

    @Override
    public List<Problem> analyze( File projectDir )
    {
		final List<Problem> problems = new ArrayList<>();

		final List<File> files = fileHelper.findFiles(projectDir, ".properties" );

		for (File file : files) {
			checkPropertiesFile(file, problems);
		}

		return problems;
    }

	public void checkPropertiesFile(File file, List<Problem> problems)
	{
		pc.setFile(file);

		for (String property : properties) {
			StringBuffer sb = new StringBuffer();
			sb.append("Properties File Location:");
			sb.append(file.getAbsolutePath());
			sb.append(",");
			sb.append("propertie:");
			sb.append(property);
			sb.append(".");

			Problem problem = pc.checkProperty(property, sb.toString());

			if (problem != null) {
				problems.add(problem);
			}
		}
	}

}
