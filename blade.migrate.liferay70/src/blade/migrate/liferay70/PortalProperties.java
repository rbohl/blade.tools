
package blade.migrate.liferay70;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.checker.PropertyChecker;

@Component
public class PortalProperties implements ProjectMigrator
{

    private static PropertyChecker pc = new PropertyChecker();

    private List<String> properties = new ArrayList<String>();

    public PortalProperties() {
    	properties.add( "servlet.service.events.pre" );
        properties.add( "release.info.previous.build.number" );
	}

    @Override
    public List<Problem> analyze( File projectDir )
    {
		final List<Problem> problems = new ArrayList<>();

		final List<File> files = findPropertiesFiles(projectDir);

		for (File file : files) {
			checkPropertiesFile(file, problems);
		}

		return problems;
    }

    private List<File> findPropertiesFiles(File projectDir) {
		final List<File> files = new ArrayList<>();

		final FileVisitor<Path> visitor = new SimpleFileVisitor<Path>(){
        	@Override
        	public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        		File file = path.toFile();

        		if(file.isFile())
        		{
        			if(file.getName().endsWith(".properties"))
        			{
        				files.add(file);
        			}
        		}

        		return super.visitFile(path, attrs);
        	}
        };

		try {
			Files.walkFileTree(projectDir.toPath(), visitor);
		} catch (IOException e) {
			// TODO properly log exception
			e.printStackTrace();
		}

		return files;

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
