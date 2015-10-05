package blade.migrate.core;

import blade.migrate.api.AutoMigrateException;
import blade.migrate.api.AutoMigrator;
import blade.migrate.api.Problem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.internal.core.util.Util;
import org.eclipse.text.edits.TextEdit;

public abstract class ImportStatementMigrator extends JavaFileMigrator implements AutoMigrator {

	private static final String PREFIX = "import:";
	private final Map<String, String> _imports = new HashMap<>();

	public ImportStatementMigrator(String[] imports, String[] fixedImports) {
		for(int i = 0; i < imports.length; i++) {
			_imports.put(imports[i], fixedImports[i]);
		}
	}

	@Override
	public void correctProblems(File file, List<Problem> problems) throws AutoMigrateException {
		final List<String> importsToRewrite = new ArrayList<>();

		for (Problem problem : problems) {
			if (problem.autoCorrectContext instanceof String) {
				final String importData = problem.autoCorrectContext;

				if (importData != null && importData.startsWith(PREFIX)) {
					final String importValue = importData.substring(PREFIX.length());

					if (_imports.containsKey(importValue)) {
						importsToRewrite.add(importValue);
					}
				}
			}
		}

		if (importsToRewrite.size() > 0) {
			ICompilationUnit source = null;
			final FileHelper fileHelper = new FileHelper();
			final IFile javaFile;

			try {
				javaFile = WorkspaceUtil.getFileFromWorkspace(file, new WorkspaceHelper());
			} catch (CoreException | IOException e1) {
				throw new AutoMigrateException("Unable to get java file as IFile, is project a java project?", e1);
			}

			try {
				source = JavaCore.createCompilationUnitFrom(javaFile);
			}
			catch (Exception e) {
				throw new AutoMigrateException("Could not get compilation unit for file: " + file.getName(), e);
			}

			final ImportRewrite importRewrite;

			try {
				importRewrite = ImportRewrite.create(source, true);
			} catch (JavaModelException e1) {
				e1.printStackTrace();
				throw new AutoMigrateException("Unable to create import rewrite action: " + file.getName(), e1);
			}

			for (String importToRewrite : importsToRewrite) {
				importRewrite.removeImport(importToRewrite);

				final String newImport = _imports.get(importToRewrite);

				importRewrite.addImport(newImport);
			}

			if (importRewrite.hasRecordedChanges()) {
				try {
					final TextEdit textEdit = importRewrite.rewriteImports(new NullProgressMonitor());
					final String newSource = Util.editedString(source.getSource(), textEdit);

					javaFile.setContents(new ByteArrayInputStream(newSource.getBytes()), IResource.FORCE, null);
					fileHelper.writeFile(file, newSource);
				} catch (CoreException | IOException e) {
					throw new AutoMigrateException("Auto correct failed to rewrite imports", e);
				}
			}
		}
	}

	@Override
	public List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		for (String importName : _imports.keySet()) {
			final SearchResult importResult = javaFileChecker.findImport(importName);

			if (importResult != null) {
				importResult.autoCorrectContext = PREFIX + importName;

				searchResults.add(importResult);
			}
		}

		return searchResults;
	}

}
