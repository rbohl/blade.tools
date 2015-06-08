
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.checker.JavaChecker;
import blade.migrate.core.FileHelper;

@Component
public class Imports implements ProjectMigrator
{

    private static JavaChecker jc = new JavaChecker();

    public static final String VERSION_1_6 = "1.6";

    private final List<String> imports = new ArrayList<String>();
    private final FileHelper fileHelper = new FileHelper();

    public Imports() {
    	 imports.add( "com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet" );
         imports.add( "javax.portlet.ActionRequest" );
	}

    @Override
    public List<Problem> analyze( File projectDir )
    {
        final List<Problem> problems = new ArrayList<>();

        final List<File> files = fileHelper.findFiles(projectDir, ".java" );

		for (File file : files) {
			checkJavaFile(file, problems);
		}

		return problems;
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
