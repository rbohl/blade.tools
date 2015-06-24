package blade.migrate.api;

public interface Reporter {
	public void beginReporting(int format);

	public void endReporting();

	public void report(Problem problem);

	public int FORMAT_LONG = 1 << 2;

	public int FORMAT_SHORT = 1 << 1;

}