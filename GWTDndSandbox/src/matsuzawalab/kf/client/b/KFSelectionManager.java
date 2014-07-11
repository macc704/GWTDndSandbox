package matsuzawalab.kf.client.b;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.util.Area;
import com.allen_sauer.gwt.dnd.client.util.CoordinateArea;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public class KFSelectionManager {

	public KFSelectionManager() {
	}

	public void makeMultipleSelection(
			final PickupDragController pickupDragController) {
		
		pickupDragController.setBehaviorMultipleSelection(true);

		final AbsolutePanel boundaryPanel = pickupDragController
				.getBoundaryPanel();
		boundaryPanel.addDomHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				int mouseX = event.getRelativeX(boundaryPanel.getElement());
				int mouseY = event.getRelativeY(boundaryPanel.getElement());
				Widget w = find(mouseX, mouseY);
				if (w == null) {
					pickupDragController.clearSelection();
				}
			}

			private Widget find(int x, int y) {
				Location mouseLoc = new CoordinateLocation(x, y);
				int len = boundaryPanel.getWidgetCount();
				for (int i = 0; i < len; i++) {
					Widget each = boundaryPanel.getWidget(i);
					int wx = boundaryPanel.getWidgetLeft(each);
					int wy = boundaryPanel.getWidgetTop(each);
					int ww = each.getOffsetWidth();
					int wh = each.getOffsetHeight();
					Area area = new CoordinateArea(wx, wy, wx + ww, wy + wh);
					if (area.intersects(mouseLoc)) {
						return each;
					}
				}
				return null;
			}
		}, MouseDownEvent.getType());
	}
}
