package blade.migrate.ide;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

@Component(
	property = {
		Constants.SERVICE_RANKING + ":Integer=1"
	}
)
public class IDEReporter implements Reporter {

	@Override
	public void beginReporting(int format) {
	}

	@Override
	public void endReporting() {
	}

	@Override
	public void report(Problem problem) {
		File file = problem.file;

		IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();

		System.out.println(file.toURI());
		IFile[] files = ws.findFilesForLocationURI(file.toURI());
/*		System.out.println(files);*/
		if( files != null && files.length > 0) {
			IFile wsFile = files[0];

			try {
				IMarker marker = wsFile.createMarker( "blade.migrate.ide.MigrationMarker" );
				marker.setAttribute( IMarker.SEVERITY, IMarker.SEVERITY_ERROR );
	            marker.setAttribute( IMarker.MESSAGE, problem.title );
	            marker.setAttribute( IMarker.LINE_NUMBER, problem.lineNumber );
	            marker.setAttribute( IMarker.LOCATION, problem.file.getName() );
	            marker.setAttribute( IMarker.CHAR_START, problem.startOffset);
	            marker.setAttribute( IMarker.CHAR_END, problem.endOffset);


	            IMarker[] markers = wsFile.findMarkers("blade.migrate.ide.MigrationMarker", false, IResource.DEPTH_INFINITE);
	            for(IMarker m :markers) {
	            	System.out.println(m.getType());
	            	System.out.println(m.getResource().getName());
	            }
	            System.out.println(markers);
			} catch (CoreException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
