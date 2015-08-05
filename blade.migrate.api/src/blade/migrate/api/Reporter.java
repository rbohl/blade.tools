package blade.migrate.api;

import java.io.OutputStream;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Reporter {

	public void beginReporting(int format, OutputStream output);

	public void endReporting();

	public void report(Problem problem);

	public int FORMAT_LONG = 1 << 2;

	public int FORMAT_SHORT = 1 << 1;

}