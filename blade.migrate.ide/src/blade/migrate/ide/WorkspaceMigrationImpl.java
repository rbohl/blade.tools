package blade.migrate.ide;

import blade.migrate.api.MigrationListener;
import blade.migrate.api.Problem;
import blade.migrate.api.WorkspaceMigration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	service = { WorkspaceMigration.class, MigrationListener.class}
)
public class WorkspaceMigrationImpl implements  WorkspaceMigration, MigrationListener {

	ComponentContext _context;
	File _migrationFile;

	@Activate
	public void activate(ComponentContext context) {
		_context = context;
		_migrationFile = _context.getBundleContext().getDataFile(WorkspaceMigration.class.getName() + ".xml");
	}

	@Override
	public void problemsFound(List<Problem> problems) {
		XMLMemento memento = null;

		if (_migrationFile.exists()) {
			try {
				memento = XMLMemento.createReadRoot(new BufferedReader(new FileReader(_migrationFile)));
			} catch (WorkbenchException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			_migrationFile.getParentFile().mkdirs();
			try {
				_migrationFile.createNewFile();
				memento = XMLMemento.createWriteRoot(WorkspaceMigration.class.getSimpleName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (memento != null) {
			for (Problem problem : problems) {
				IMemento problemTag = memento.createChild(Problem.class.getSimpleName());
				try {
					saveProblem(problem, problemTag);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(_migrationFile));
			memento.save(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveProblem(Problem problem, IMemento problemTag) throws IOException {
		problemTag.putInteger("number", problem.number);
		problemTag.putString("title", problem.title);
		problemTag.putString("summary", problem.summary);
		problemTag.putString("url", problem.url);
		problemTag.putString("type", problem.type);
		problemTag.putString("ticket", problem.ticket);
		problemTag.putString("file", problem.file.getCanonicalPath());
		problemTag.putInteger("lineNumber", problem.lineNumber);
		problemTag.putInteger("startOffset", problem.startOffset);
		problemTag.putInteger("endOffset", problem.endOffset);
	}

	@Override
	public List<Problem> getStoredProblems(boolean includeResolved) {
		List<Problem> storedProblems = new ArrayList<>();

		try {
			XMLMemento memento = XMLMemento.createReadRoot(new BufferedReader(new FileReader(_migrationFile)));

			IMemento[] problems = memento.getChildren(Problem.class.getSimpleName());

			for (IMemento problem : problems) {
				Boolean resolved = problem.getBoolean("resolved");

				if (resolved != null && resolved.booleanValue() == true && includeResolved) {
					storedProblems.add(readProblem(problem));
				}
				else {
					storedProblems.add(readProblem(problem));
				}
			}
		} catch (WorkbenchException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storedProblems;
	}

	private Problem readProblem(IMemento problem) {
		return new Problem(
				problem.getString("title"),
				problem.getString("url"),
				problem.getString("summary"),
				problem.getString("type"),
				problem.getString("ticket"),
				new File(problem.getString("file")),
				problem.getInteger("lineNumber"),
				problem.getInteger("startOffset"),
				problem.getInteger("endOffset"));
	}



}
