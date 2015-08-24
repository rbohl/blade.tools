package blade.migrate.ide;

import blade.migrate.api.Problem;
import blade.migrate.api.Reporter;

import java.io.File;
import java.io.OutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		Constants.SERVICE_RANKING + ":Integer=100",
		"format:String=ide"
	},
	immediate = true
)
public class IDEReporter implements Reporter {

	public IDEReporter() {

	}

	@Override
	public void beginReporting(int format, OutputStream output) {
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
				IMarker marker = wsFile.createMarker( MigrationConstants.MIGRATION_MARKER_TYPE );
				marker.setAttribute( IMarker.SEVERITY, IMarker.SEVERITY_ERROR );
	            marker.setAttribute( IMarker.MESSAGE, problem.title );
	            marker.setAttribute( IMarker.LINE_NUMBER, problem.lineNumber );
	            marker.setAttribute( IMarker.LOCATION, problem.file.getName() );
	            marker.setAttribute( IMarker.CHAR_START, problem.startOffset);
	            marker.setAttribute( IMarker.CHAR_END, problem.endOffset);
	            marker.setAttribute("summary", problem.summary);
	            marker.setAttribute("ticket", problem.ticket);


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
