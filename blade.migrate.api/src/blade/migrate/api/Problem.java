package blade.migrate.api;

import java.io.File;

public class Problem
{
    public int number;
    public String title;
    public String url;
    public String summary;
    public String type;
    public String ticket;
    public File file;
    public int lineNumber;

	public Problem() {
		super();
	}

	public Problem( String title, String url,
			String summary, String type, String ticket, File file,
			int lineNumber) {
		super();
		this.title = title;
		this.url = url;
		this.summary = summary;
		this.type = type;
		this.ticket = ticket;
		this.file = file;
		this.lineNumber = lineNumber;
	}

}
