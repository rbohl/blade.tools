package blade.migrate.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jst.jsp.core.internal.java.JSPTranslation;
import org.eclipse.jst.jsp.core.internal.java.JSPTranslator;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

import aQute.lib.io.IO;

public class JSPFileChecker extends JavaFileChecker {

	public JSPFileChecker(File file) {
		super(file);

		getTranslation(file);
	}

	public void addNaturesToProject( IProject proj, String[] natureIds, IProgressMonitor monitor )
	        throws CoreException {
        IProjectDescription description = proj.getDescription();

        String[] prevNatures = description.getNatureIds();
        String[] newNatures = new String[prevNatures.length + natureIds.length];

        System.arraycopy( prevNatures, 0, newNatures, 0, prevNatures.length );

        for( int i = prevNatures.length; i < newNatures.length; i++ ) {
            newNatures[i] = natureIds[i - prevNatures.length];
        }

        description.setNatureIds( newNatures );
        proj.setDescription( description, monitor );
    }

	private JSPTranslation createJSPTranslation() {
		try {
			IJavaProject project = getDefaultJavaProject();
			IFile jspFile = getJSPFile();

			IDOMModel jspModel = (IDOMModel) StructuredModelManager.getModelManager().getModelForRead(jspFile);
			IDOMDocument domDocument = jspModel.getDocument();
			IDOMNode domNode = (IDOMNode) domDocument.getDocumentElement();

			IProgressMonitor npm = new NullProgressMonitor();
			JSPTranslator translator = new JSPTranslator();

			if (domNode != null) {
				translator.reset((IDOMNode) domDocument.getDocumentElement(), npm);
			}
			else {
				translator.reset((IDOMNode) domDocument.getFirstChild(), npm);
			}

			translator.translate();
			_translation = new JSPTranslation(project, translator);

			return _translation;
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected SearchResult createSearchResult(int startOffset, int endOffset,
			int startLine, int endLine) {

		try {
			JSPTranslation translation = getTranslation(getFile());

			int jspStartOffset = translation.getJspOffset(startOffset);
			int jspEndOffset = translation.getJspOffset(endOffset);

			IDOMModel jspModel = (IDOMModel) StructuredModelManager.getModelManager().getModelForRead(getJSPFile());
			IDOMDocument domDocument = jspModel.getDocument();

			IStructuredDocument structuredDocument = domDocument.getStructuredDocument();
			int jspStartLine = structuredDocument.getLineOfOffset(jspStartOffset) + 1;
			int jspEndLine = structuredDocument.getLineOfOffset(jspEndOffset);

			return super.createSearchResult(jspStartOffset, jspEndOffset, jspStartLine, jspEndLine);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.createSearchResult(startOffset, endOffset, startLine, endLine);
	}

	private IJavaProject getDefaultJavaProject() throws CoreException {
		IProject checkerProject = ResourcesPlugin.getWorkspace().getRoot().getProject("__jsp_migration__");

		if (checkerProject.exists()) {
			checkerProject.open(new NullProgressMonitor());
			return JavaCore.create(checkerProject);
		}

		IProgressMonitor monitor = new NullProgressMonitor();

		checkerProject.create(monitor);
		checkerProject.open(monitor);
		addNaturesToProject(checkerProject, new String[] { JavaCore.NATURE_ID }, monitor);

		return JavaCore.create(checkerProject);
	}

	@Override
	protected char[] getJavaSource() {
		JSPTranslation translation = getTranslation(getFile());

		return translation.getJavaText().toCharArray();
	}

	private IFile getJSPFile() throws CoreException, IOException {
		IJavaProject project = getDefaultJavaProject();

		IFile jspFile = project.getProject().getFile(getFile().getName());

		final IProgressMonitor npm = new NullProgressMonitor();

		if (jspFile.exists()) {
			jspFile.delete(IFile.FORCE, npm);
		}

		jspFile.create(new ByteArrayInputStream(IO.read(getFile())), IFile.FORCE, npm);

		return jspFile;
	}

	private JSPTranslation getTranslation(File file) {
		try {
			synchronized (_map) {
				WeakReference<JSPTranslation> translationRef = _map.get(file);

				if (translationRef == null || translationRef.get() == null) {
					final JSPTranslation newTranslation = createJSPTranslation();

					_map.put(file, new WeakReference<JSPTranslation>(newTranslation));

					_translation = newTranslation;
				}
				else {
					_translation = translationRef.get();
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		return _translation;
	}

	private static Map<File, WeakReference<JSPTranslation>> _map = new WeakHashMap<>();
	private JSPTranslation _translation;
}
