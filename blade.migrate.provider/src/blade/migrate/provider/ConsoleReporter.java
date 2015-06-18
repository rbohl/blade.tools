package blade.migrate.provider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;
import dnl.utils.text.table.TextTable;

@Component
public class ConsoleReporter implements Reporter {
	private final List<String> columnNames = new ArrayList<String>();
	private final List<Object[]> rowData = new ArrayList<Object[]>();

	@Override
	public void beginReporting(int format) {
		if (format == FORMAT_SHORT) {
			columnNames.add("Title");
			columnNames.add("Type");
			columnNames.add("File");
			columnNames.add("Line");
		}
		else{
			columnNames.add("Title");
			columnNames.add("Summary");
			columnNames.add("Url");
			columnNames.add("Type");
			columnNames.add("Ticket");
			columnNames.add("File");
			columnNames.add("Line");
		}
	}

	@Override
	public void report(Problem problem) {
		if (columnNames.size() == 4) {
			rowData.add(new Object[] { problem.title, problem.type,
					problem.file.getName(), problem.lineNumber });
		} else {
			rowData.add(new Object[] { problem.title, problem.summary,
					problem.url, problem.type, problem.ticket,
					problem.file.getName(), problem.lineNumber });
		}
	}

	@Override
	public void endReporting() {
		Object[][] data = new Object[rowData.size()][columnNames.size()];

		for(int i = 0; i < rowData.size(); i++){
			for(int j = 0; j < columnNames.size(); j++){
				data[i][j] = rowData.get(i)[j];
			}
		}

		TextTable tt = new TextTable(columnNames.toArray(new String[0]) , data);
		tt.setAddRowNumbering(true);
		tt.printTable();

		this.columnNames.clear();
		this.rowData.clear();
	}
}
