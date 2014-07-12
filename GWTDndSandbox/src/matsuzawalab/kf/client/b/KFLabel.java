package matsuzawalab.kf.client.b;

import matsuzawalab.kf.client.a.WindowController;
import matsuzawalab.kf.client.a.WindowPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;

public class KFLabel extends SimplePanel {

	private FocusPanel handlePanel;
	private AbsolutePanel mainPanel;
	private HTML titleWidget;
	private String title;
	private String content;

	public KFLabel(String title, String content) {
		this.title = title;
		this.content = content;

		handlePanel = new FocusPanel();
		this.setWidget(handlePanel);
		mainPanel = new AbsolutePanel();
		handlePanel.setWidget(mainPanel);

		mainPanel.setSize("100px", "27px");
		mainPanel.getElement().getStyle().setProperty("borderStyle", "solid");
		mainPanel.getElement().getStyle().setProperty("borderWidth", "1px");
		mainPanel.getElement().getStyle().setProperty("borderColor", "red");
		// panel.getElement().setDraggable(Element.DRAGGABLE_TRUE);

		HTML icon = new HTML();
		icon.setSize("100px", "27px");
		icon.getElement().getStyle().setProperty("backgroundColor", "gray");
		mainPanel.add(icon, 0, 0);

		titleWidget = new HTML("<a>" + title + "</a>");
		titleWidget.getElement().getStyle().setProperty("cursor", "pointer");
		mainPanel.add(titleWidget, 20, 0);
	}

	public void bindEvents(final WindowController windowController,
			final PickupDragController pickupDragController) {

		this.addDomHandler(new ContextMenuHandler() {
			public void onContextMenu(ContextMenuEvent event) {
				int x = event.getNativeEvent().getClientX();
				int y = event.getNativeEvent().getClientY();

				MenuBar popupMenuBar = new MenuBar(true);
				// System.out.println("A");
				event.preventDefault();
				event.stopPropagation();
				final PopupPanel popup = new PopupPanel(true);
				popup.add(popupMenuBar);
				popupMenuBar.addItem(new MenuItem("open in window",
						new ScheduledCommand() {
							public void execute() {
								Window.open("http://www.google.co.jp",
										"_blank",
										"width=640, height=480, resizable");
								popup.hide(true);
							};
						}));
				popupMenuBar.addItem(new MenuItem("menu2",
						new ScheduledCommand() {
							public void execute() {
								System.out.println("menu2 pressed");
								popup.hide(true);
							};
						}));
				// popupMenuBar.addItem(new MenuItem("test2"));
				// popup.add(new Button("Test2"));
				popup.setPopupPosition(x, y);
				popup.show();
			}

		}, ContextMenuEvent.getType());

		pickupDragController.makeDraggable(this, handlePanel);

		class KFLabelMouseHandler implements MouseUpHandler, MouseDownHandler,
				MouseMoveHandler {
			int screenX;
			int screenY;
			int clientX;
			int clientY;
			int button;
			boolean altKey, ctrlKey, metaKey, shiftKey;

			private MouseDownEvent storedEvent;

			@Override
			public void onMouseDown(MouseDownEvent event) {
				// System.out.println("down");
				if ((event.getNativeButton() & NativeEvent.BUTTON_LEFT) != NativeEvent.BUTTON_LEFT) {
					return;
				}
				if (event.isControlKeyDown() || event.isAltKeyDown()
						|| event.isMetaKeyDown()) {
					return;
				}
				screenX = event.getScreenX();
				screenY = event.getScreenY();
				clientX = event.getRelativeX(handlePanel.getElement());
				clientY = event.getRelativeY(handlePanel.getElement());
				button = event.getNativeButton();
				altKey = event.isAltKeyDown();
				ctrlKey = event.isControlKeyDown();
				metaKey = event.isMetaKeyDown();
				shiftKey = event.isShiftKeyDown();
				storedEvent = event;
				event.stopPropagation();
			}

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				// System.out.println("move");
				if (storedEvent != null) {
					NativeEvent ev = getElement().getOwnerDocument()
							.createMouseDownEvent(0, screenX, screenY, clientX,
									clientY, ctrlKey, altKey, shiftKey,
									metaKey, button);
					storedEvent.setNativeEvent(ev);
					KFLabel.this.delegateEvent(handlePanel, storedEvent);
					storedEvent = null;
				}
			}

			@Override
			public void onMouseUp(MouseUpEvent event) {
				// System.out.println("up");
				if (storedEvent != null) {
					storedEvent = null;
					event.preventDefault();
					event.stopPropagation();
					openWindow(windowController, pickupDragController);
				}
			}
		}
		KFLabelMouseHandler mouseHandler = new KFLabelMouseHandler();
		titleWidget.addMouseUpHandler(mouseHandler);
		titleWidget.addMouseMoveHandler(mouseHandler);
		titleWidget.addMouseDownHandler(mouseHandler);
	}

	private void openWindow(final WindowController windowController,
			final PickupDragController pickupDragController) {
		//HTML html1 = new HTML(content.replaceAll("\n", "<br>\n"));
		TextArea html1 = new TextArea();
		html1.setText(content);
		html1.addStyleName("demo-resize-html");
		WindowPanel windowPanel1 = new WindowPanel(windowController, title,
				html1, false, pickupDragController);		
		pickupDragController.getBoundaryPanel().add(windowPanel1, 20, 20);
		windowPanel1.setWindowSize(400, 300);
	}

}
