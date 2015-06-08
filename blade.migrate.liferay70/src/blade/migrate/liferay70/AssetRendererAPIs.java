
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
public class AssetRendererAPIs implements ProjectMigrator
{

    private static JavaChecker jc = new JavaChecker();

    private static final String methodName = "getSummary";
    private final List<String> oldParameters = new ArrayList<String>();
    private final List<String> newParameters = new ArrayList<String>();
    private final FileHelper fileHelper = new FileHelper();

    public AssetRendererAPIs() {
    	oldParameters.add("Locale");
    	newParameters.add( "PortletRequest" );
    	newParameters.add( "PortletResponse" );
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

	public void checkJavaFile(File file, List<Problem> problems) {
		jc.setFile(file);

		StringBuffer sb = new StringBuffer();
		sb.append("Class Location:");
		sb.append(file.getAbsolutePath());
		sb.append(",");
		sb.append("methond:");
		sb.append(methodName);
		sb.append(".");

		Problem problem = jc.checkMethod(methodName, oldParameters, sb.toString());

		if (problem != null) {
			problems.add(problem);
		}

	}

}
