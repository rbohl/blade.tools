package blade.migrate.core;

import java.io.ByteArrayInputStream;
import java.io.File;

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
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

import aQute.lib.io.IO;

public class JSPFileChecker extends JavaFileChecker {

	private JSPTranslator _translator;
	private JSPTranslation _translation;

	public JSPFileChecker(File file) {
		super(file);
	}

	@Override
	protected char[] getJavaSource() {
		try {
			IJavaProject project = getDefaultJavaProject();
			_translator = new JSPTranslator();

			IFile jspFile = project.getProject().getFile(getFile().getName());

			final IProgressMonitor npm = new NullProgressMonitor();

			if (jspFile.exists()) {
				jspFile.delete(IFile.FORCE, npm);
			}

			jspFile.create(new ByteArrayInputStream(IO.read(getFile())), IFile.FORCE, npm);

			IDOMModel jspModel = (IDOMModel) StructuredModelManager.getModelManager().getModelForRead(jspFile);
			IDOMDocument domDocument = jspModel.getDocument();
			IDOMNode domNode = (IDOMNode) domDocument.getDocumentElement();

			if (domNode != null) {
				_translator.reset((IDOMNode) domDocument.getDocumentElement(), npm);
			}
			else {
				_translator.reset((IDOMNode) domDocument.getFirstChild(), npm);
			}

			_translator.translate();
			_translation = new JSPTranslation(project, _translator);
			return _translation.getJavaText().toCharArray();
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return null;
	}

	private IJavaProject getDefaultJavaProject() throws CoreException {
		IProject checkerProject = ResourcesPlugin.getWorkspace().getRoot().getProject("__jsp_checker__");

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

	public void addNaturesToProject( IProject proj, String[] natureIds, IProgressMonitor monitor )
	        throws CoreException {
        IProjectDescription description = proj.getDescription();

        String[] prevNatures = description.getNatureIds();
        String[] newNatures = new String[prevNatures.length + natureIds.length];

        System.arraycopy( prevNatures, 0, newNatures, 0, prevNatures.length );

        for( int i = prevNatures.length; i < newNatures.length; i++ )
        {
            newNatures[i] = natureIds[i - prevNatures.length];
        }

        description.setNatureIds( newNatures );
        proj.setDescription( description, monitor );
    }
}
