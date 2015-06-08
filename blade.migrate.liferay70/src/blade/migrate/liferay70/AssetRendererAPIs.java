
package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.checker.JavaChecker;

@Component(
	property = { "file.extension=java" }
)
public class AssetRendererAPIs implements FileMigrator
{

    private static JavaChecker jc = new JavaChecker();

    private static final String methodName = "getSummary";
    private final List<String> oldParameters = new ArrayList<String>();
    private final List<String> newParameters = new ArrayList<String>();

    public AssetRendererAPIs() {
    	oldParameters.add("Locale");
    	newParameters.add( "PortletRequest" );
    	newParameters.add( "PortletResponse" );
	}

    @Override
    public List<Problem> analyzeFile( File file )
    {
        final List<Problem> problems = new ArrayList<>();

        checkJavaFile(file, problems);

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
