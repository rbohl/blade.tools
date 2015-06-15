package blade.migrate.api;

public interface Reporter {

	public void beginReporting();

	public void report(Problem problem);

	public void endReporting();

}
