package matsuzawalab.kf.client.b;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.util.Area;
import com.allen_sauer.gwt.dnd.client.util.CoordinateArea;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class KFSelectionManager implements MouseDownHandler, MouseMoveHandler,
		MouseUpHandler {

	public KFSelectionManager() {
	}

	private KFPickupDragController pickupDragController;
	private AbsolutePanel boundaryPanel;

	public void makeMultipleSelection(
			final KFPickupDragController pickupDragController) {
		this.pickupDragController = pickupDragController;
		this.boundaryPanel = pickupDragController.getBoundaryPanel();

		pickupDragController.setBehaviorMultipleSelection(true);

		boundaryPanel.addDomHandler(this, MouseDownEvent.getType());
		boundaryPanel.addDomHandler(this, MouseMoveEvent.getType());
		boundaryPanel.addDomHandler(this, MouseUpEvent.getType());
	}

	private Widget marquee;
	private int pressX;
	private int pressY;

	@Override
	public void onMouseDown(MouseDownEvent event) {
		int mouseX = event.getRelativeX(boundaryPanel.getElement());
		int mouseY = event.getRelativeY(boundaryPanel.getElement());
		Widget w = find(mouseX, mouseY);
		if (w == null) {
			pickupDragController.clearSelection();

			marquee = new HTML("");
			pressX = mouseX;
			pressY = mouseY;
			marquee.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
			marquee.getElement().getStyle().setBorderColor("#0000FF");
			marquee.getElement().getStyle().setBorderWidth(2, Unit.PT);
			marquee.setSize("0px", "0px");
			boundaryPanel.add(marquee, pressX, pressY);
			event.preventDefault();
			event.stopPropagation();
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (marquee == null) {
			return;
		}
		int mouseX = event.getRelativeX(boundaryPanel.getElement());
		int mouseY = event.getRelativeY(boundaryPanel.getElement());
		int x = Math.min(mouseX, pressX);
		int y = Math.min(mouseY, pressY);
		int w = Math.abs(mouseX - pressX);
		int h = Math.abs(mouseY - pressY);
		boundaryPanel.setWidgetPosition(marquee, x, y);
		marquee.setSize(w + "px", h + "px");
		event.preventDefault();
		event.stopPropagation();
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if (marquee != null) {
			for (Widget w : find(marquee)) {
				pickupDragController.toggleSelection(w);
			}
			boundaryPanel.remove(marquee);
			marquee = null;
			event.preventDefault();
			event.stopPropagation();
		}
	}

	private List<Widget> find(Widget hasSearchArea) {
		List<Widget> found = new ArrayList<Widget>();
		Area searchArea = widgetArea(hasSearchArea);
		int len = boundaryPanel.getWidgetCount();
		for (int i = 0; i < len; i++) {
			Widget each = boundaryPanel.getWidget(i);
			Area area = widgetArea(each);
			if (searchArea.intersects(area)) {
				found.add(each);
			}
		}
		return found;
	}

	private Widget find(int x, int y) {
		Location mouseLoc = new CoordinateLocation(x, y);
		int len = boundaryPanel.getWidgetCount();
		for (int i = 0; i < len; i++) {
			Widget each = boundaryPanel.getWidget(i);
			Area area = widgetArea(each);
			if (area.intersects(mouseLoc)) {
				return each;
			}
		}
		return null;
	}

	private Area widgetArea(Widget w) {
		int wx = boundaryPanel.getWidgetLeft(w);
		int wy = boundaryPanel.getWidgetTop(w);
		int ww = w.getOffsetWidth();
		int wh = w.getOffsetHeight();
		Area area = new CoordinateArea(wx, wy, wx + ww, wy + wh);
		return area;
	}
}
