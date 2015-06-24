package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Problem;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.SearchResult;

public class JavaMethodMigrator implements FileMigrator {

	ComponentContext context;
	String methodType;
	String methodName;
	String[] methodParamTypes;
	String problemTitle;
	String problemUrl;
	String problemSummary;
	String problemType;
	String problemTickets;


	@Activate
	public void activate(ComponentContext ctx) {
		 this.context = ctx;

		 final Dictionary<String, Object> properties = this.context.getProperties();
		 this.methodType = (String) properties.get("method.type");
		 this.methodName = (String) properties.get("method.name");
		 this.methodParamTypes = ( (String) properties.get("method.param.types") ).split(",");
		 this.problemTitle = (String) properties.get("problem.title");
		 this.problemUrl = (String) properties.get("problem.url");
		 this.problemSummary = (String) properties.get("problem.summary");
		 this.problemType = (String) properties.get("problem.type");
		 this.problemTickets = (String) properties.get("problem.tickets");
	 }

	@Override
	public List<Problem> analyzeFile(File file) {

		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		final List<Problem> problems = new ArrayList<>();

		SearchResult methodResult = null;

		if ("declaration".equals(methodType)) {
			methodResult = javaFileChecker.findMethodDeclartion(this.methodName,
					this.methodParamTypes);
		}
		else {
			methodResult = javaFileChecker.findMethodInvocation(this.methodName,
					this.methodParamTypes);
		}

		if (methodResult != null) {
			problems.add(new Problem(this.problemTitle, this.problemUrl, this.problemSummary, this.problemType,
					this.problemTickets, file, methodResult.startLine));
		}

		return problems;
	}

}