package blade.migrate.ide;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

public class MigrationLabelProvider extends StyledCellLabelProvider {

	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		StyledString text = new StyledString();


		if (element instanceof String) {
			text.append(element.toString());
		}
		else if (element instanceof IMarker ) {
			try {
				text.append((String) ((IMarker)element).getAttribute(IMarker.MESSAGE));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		cell.setText(text.toString());
	    cell.setStyleRanges(text.getStyleRanges());

		super.update(cell);
	}
}
