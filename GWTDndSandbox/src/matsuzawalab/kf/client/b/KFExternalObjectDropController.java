package matsuzawalab.kf.client.b;

import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class KFExternalObjectDropController implements DragEnterHandler,
		DragLeaveHandler, DragOverHandler, DropHandler {

	private IKExternalObjectDropHandler handler;

	private Widget widget;

	public void setDroppable(Widget widget, IKExternalObjectDropHandler handler) {
		this.handler = handler;
		this.widget = widget;
		widget.addDomHandler(this, DragEnterEvent.getType());
		widget.addDomHandler(this, DragLeaveEvent.getType());
		widget.addDomHandler(this, DragOverEvent.getType());
		widget.addDomHandler(this, DropEvent.getType());
	}

	public void onDragEnter(DragEnterEvent event) {
		event.preventDefault();
		event.stopPropagation();

		// below does not work well so I pend it (yoshiaki)
		Image image = new Image();
		image.setUrl("http://builder.ikit.org/image/knowledge_forum_header.png");
		event.getDataTransfer().setDragImage(image.getElement(), 0, 0);
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

		int wx = widget.getAbsoluteLeft();
		int wy = widget.getAbsoluteTop();
		int x = event.getNativeEvent().getClientX() - wx;
		int y = event.getNativeEvent().getClientY() - wy;

		handler.dropped(new KFDataTransfer(event.getDataTransfer()), x, y);
	}
}
