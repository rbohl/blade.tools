package blade.migrate.core;

import java.io.File;

public class SearchResult {

	public SearchResult(File file, int startOffset, int endOffset,
			int startLine, int endLine) {

		this.file = file;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.startLine = startLine;
		this.endLine = endLine;
	}

	public final int endLine;
	public final int endOffset;
	public final File file;
	public final int startLine;
	public final int startOffset;

}