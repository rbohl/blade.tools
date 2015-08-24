package blade.migrate.ide.view;

import blade.migrate.ide.MigrationConstants;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class MigrationContentProvider implements ITreeContentProvider {

	private Object _input;
	private IMarker[] _markers = new IMarker[0];

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_input = newInput;

		if (_input instanceof IWorkspaceRoot) {
			IWorkspaceRoot root = (IWorkspaceRoot) _input;

			try {
				_markers = root.findMarkers(MigrationConstants.MIGRATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return _markers;
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		if (parentElement instanceof IMarker) {
			IMarker marker = (IMarker) parentElement;

			try {
				return new String[] { (String) marker.getAttribute("summary"), (String) marker.getAttribute("ticket") };
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof IMarker;
	}

}
