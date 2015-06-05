
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
import blade.migrate.checker.JavaChecker;

@Component
public class Imports implements ProjectMigrator
{

    private static JavaChecker jc = new JavaChecker();

    public static final String VERSION_1_6 = "1.6";

    private List<String> imports = new ArrayList<String>();

    public Imports() {
    	 imports.add( "com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet" );
         imports.add( "javax.portlet.ActionRequest" );
	}

    @Override
    public List<Problem> analyze( File projectDir )
    {
        final List<Problem> problems = new ArrayList<>();

		final List<File> files = findJavaFiles(projectDir);

		for (File file : files) {
			checkJavaFile(file, problems);
		}

		return problems;
    }

	private List<File> findJavaFiles(File projectDir) {
		final List<File> files = new ArrayList<>();

		final FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				File file = path.toFile();

				if (file.isFile()) {
					if (file.getName().endsWith(".java")) {
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

    public void checkJavaFile( File file, List<Problem> problems )
    {
		jc.setFile(file);

		for (String importtClass : imports) {
			StringBuffer sb = new StringBuffer();
			sb.append("Class Location:");
			sb.append(file.getAbsolutePath());
			sb.append(",");
			sb.append("import:");
			sb.append(importtClass);
			sb.append(".");

			Problem problem = jc.checkImport(importtClass, sb.toString());

			if (problem != null) {
				problems.add(problem);
			}
		}
    }

}
