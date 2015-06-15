package blade.migrate.core;

import java.io.File;

public class SearchResult {
	public final File file;
	public final int startOffset;
	public final int endOffset;
	public final int startLine;
	public final int endLine;

	public SearchResult(File file, int startOffset, int endOffset,
			int startLine, int endLine) {
		super();
		this.file = file;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.startLine = startLine;
		this.endLine = endLine;
	}

	public SearchResult(File file2,int startOffset, int endOffset) {
		this(file2, startOffset, endOffset, -1, -1);
	}
}
