package blade.migrate.ide;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class MigrationView extends ViewPart {

	private TreeViewer _viewer;

	@Override
	public void createPartControl(Composite parent) {
		_viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		_viewer.setContentProvider(new MigrationContentProvider());
		_viewer.setLabelProvider(new MigrationLabelProvider());
		_viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	@Override
	public void setFocus() {
		if (_viewer != null && _viewer.getControl() != null) {
			_viewer.getControl().setFocus();
		}
	}

}
