package matsuzawalab.kf.client.dndframework;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Widget;

public class KFExternalObjectDropController implements DragEnterHandler,
		DragLeaveHandler, DragOverHandler, DropHandler {

	private IKExternalObjectDropHandler handler;

	private Widget widget;

	private ImageElement img;

	public void setDroppable(Widget widget, IKExternalObjectDropHandler handler) {
		this.handler = handler;
		this.widget = widget;
		widget.addDomHandler(this, DragEnterEvent.getType());
		widget.addDomHandler(this, DragLeaveEvent.getType());
		widget.addDomHandler(this, DragOverEvent.getType());
		widget.addDomHandler(this, DropEvent.getType());

		img = this.widget.getElement().getOwnerDocument().createImageElement();
		img.setSrc("http://bighow.net/images/linkden2.png");
		img.setWidth(30);
		img.setHeight(30);
	}

	public void onDragEnter(DragEnterEvent event) {
		event.preventDefault();
		event.stopPropagation();

		// below does not work well so I pend it (yoshiaki)
		event.getDataTransfer().setDragImage(img, 10, 10);
	}

	public void onDragLeave(DragLeaveEvent event) {
		event.preventDefault();
		event.stopPropagation();
	}

	public void onDragOver(DragOverEvent event) {
		event.preventDefault();
		event.stopPropagation();
	}

	public void onDrop(DropEvent event) {
		event.preventDefault();
		event.stopPropagation();

		int scrollX = widget.getElement().getOwnerDocument().getScrollLeft();
		int scrollY = widget.getElement().getOwnerDocument().getScrollTop();
		int wx = widget.getAbsoluteLeft();
		int wy = widget.getAbsoluteTop();
		int x = event.getNativeEvent().getClientX() - wx + scrollX;
		int y = event.getNativeEvent().getClientY() - wy + scrollY;

		handler.dropped(new KFDataTransfer(event.getDataTransfer()), x, y);
	}
}
