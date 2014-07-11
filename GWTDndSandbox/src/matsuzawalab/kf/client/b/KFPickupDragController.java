package matsuzawalab.kf.client.b;

import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public class KFPickupDragController extends PickupDragController {

	private Set<Widget> draggables = new HashSet<Widget>();

	public KFPickupDragController(AbsolutePanel boundaryPanel) {
		super(boundaryPanel, true);
	}

	@Override
	public void makeDraggable(Widget draggable, Widget dragHandle) {
		super.makeDraggable(draggable, dragHandle);
		draggables.add(draggable);
	}

	@Override
	public void makeNotDraggable(Widget draggable) {
		super.makeNotDraggable(draggable);
		draggables.remove(draggable);
	}

	@Override
	public void toggleSelection(Widget draggable) {
		if (draggables.contains(draggable)) {
			super.toggleSelection(draggable);
		}
	}

}
