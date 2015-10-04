package blade.migrate.api;

public class AutoMigrateException extends Exception {

	public AutoMigrateException(String msg) {
		super(msg);
	}

	public AutoMigrateException(String msg, Exception e) {
		super(msg, e);
	}

}
