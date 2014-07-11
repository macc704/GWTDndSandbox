package matsuzawalab.kf.client;

import java.util.ArrayList;
import java.util.List;

import matsuzawalab.kf.client.a.WindowController;
import matsuzawalab.kf.client.a.WindowPanel;
import matsuzawalab.kf.client.b.KFSelectionManager;

import org.vectomatic.dnd.DataTransferExt;
import org.vectomatic.file.File;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.util.Area;
import com.allen_sauer.gwt.dnd.client.util.CoordinateArea;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CopyOfSandbox implements EntryPoint {
	// /**
	// * The message displayed to the user when the server cannot be reached or
	// * returns an error.
	// */
	// private static final String SERVER_ERROR = "An error occurred while "
	// + "attempting to contact the server. Please check your network "
	// + "connection and try again.";
	//
	// /**
	// * Create a remote service proxy to talk to the server-side Greeting
	// * service.
	// */
	// private final GreetingServiceAsync greetingService = GWT
	// .create(GreetingService.class);

	AbsolutePanel boundaryBasePanel;
	AbsolutePanel boundaryPanel;
	WindowController windowController;

	public void onModuleLoad() {
		RootPanel mainPanel = RootPanel.get("mainpanel");
		mainPanel.getElement().setInnerHTML("hello! dnd demo!");

		boundaryBasePanel = new AbsolutePanel();
		boundaryBasePanel.addStyleName("demo-main-boundary-panel");
		mainPanel.add(boundaryBasePanel);

		SimplePanel panel = new SimplePanel();
		panel.addStyleName("demo-WindowExample");
		boundaryBasePanel.add(panel);

		boundaryPanel = new AbsolutePanel();
		boundaryPanel.addStyleName("demo-WindowExample");
		boundaryPanel.setPixelSize(600, 400);
		panel.add(boundaryPanel);

		// onModuleLoad1();
		onModuleLoad3();
	}

	public void onModuleLoad3() {
		PickupDragController pickupDragController = new PickupDragController(
				boundaryPanel, true);
		KFSelectionManager selectionManager = new KFSelectionManager();
		selectionManager.makeMultipleSelection(pickupDragController);
		
		windowController = new WindowController(boundaryPanel);
	}

	public void onModuleLoad1() {
		// PickupDragController dragController = new PickupDragController(
		// boundaryPanel, true);
		// dragController.setBehaviorMultipleSelection(false);
		//
		//WindowExample example = new WindowExample(new DemoDragHandler(
		//		new HTML()));
		//boundaryBasePanel.add(example);
	}

	@SuppressWarnings("unused")
	public void onModuleLoad2() {
		boundaryPanel.addDomHandler(new DragEnterHandler() {
			@Override
			public void onDragEnter(DragEnterEvent event) {
				// System.out.println("hoge");
			}
		}, DragEnterEvent.getType());
		boundaryPanel.addDomHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				// System.out.println(event.getDataTransfer());
				// System.out.println("leave");
			}
		}, DragLeaveEvent.getType());
		// boundaryPanel.addDomHandler(new DropH
		boundaryPanel.addDomHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
			}
		}, DragOverEvent.getType());
		boundaryPanel.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				// System.out.println("hoge2");
				// stop default behaviour
				event.preventDefault();
				event.stopPropagation();

				System.out.println("get!");
				try {
					System.out.println(event.getDataTransfer().getData("url"));
					System.out.println(event.getDataTransfer().getData(
							"text/uri-list"));
					System.out.println(event.getDataTransfer().getData("text"));
					// Window.open(url, name, features);
					String text = event.getDataTransfer().getData("text/plain");
					System.out.println(text);
					System.out.println(event.getDataTransfer().getData(
							"text/html"));
					// System.out.println(event.getDataTransfer().getData(
					// "application/x-url"));
					// System.out.println(event.getDataTransfer().getData(
					// "text/url"));
					// System.out.println(event.getDataTransfer().getData(
					// "text/x-url"));
					DataTransferExt ext = event.getDataTransfer()
							.<DataTransferExt> cast();
					// int typelen = ext.getTypes().length();
					// System.out.println(typelen);
					// System.out.println(ext.getTypes().item(0));
					// type note implemented in Chrome
					int len = ext.getFiles().getLength();
					if (len != 1) {
						return;
					}

					File f = ext.getFiles().getItem(0);
					System.out.println(f.getName());
					System.out.println(f.getType());
					final FileReader reader = new FileReader();
					reader.addLoadEndHandler(new LoadEndHandler() {
						
						@Override
						public void onLoadEnd(LoadEndEvent event) {
							String text = reader.getStringResult();
							// System.out.println(text);
						}
					});
					reader.readAsText(f, "UTF-8");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}, DropEvent.getType());

		final List<Widget> registered = new ArrayList<Widget>();

		windowController = new WindowController(boundaryPanel);

		// instantiate the common drag controller used the less specific
		// examples
		final PickupDragController pickupDragController = new PickupDragController(
				boundaryPanel, true) {

			@Override
			public void makeDraggable(Widget draggable, Widget dragHandle) {
				super.makeDraggable(draggable, dragHandle);
				registered.add(draggable);
			}

			@Override
			public void makeNotDraggable(Widget draggable) {
				super.makeNotDraggable(draggable);
				registered.remove(draggable);
			}
		};

		pickupDragController.addDragHandler(new DragHandler() {

			@Override
			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {
			}

			@Override
			public void onPreviewDragEnd(DragEndEvent event)
					throws VetoDragException {
				// TODO Auto-generated method stub
			}

			@Override
			public void onDragStart(DragStartEvent event) {
				// System.out.println("drag start2");
			}

			@Override
			public void onDragEnd(DragEndEvent event) {
				// TODO Auto-generated method stub

			}
		});

		pickupDragController.setBehaviorMultipleSelection(true);
		boundaryPanel.addDomHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {

				// System.out.println(event.getSource());
				int mouseX = event.getRelativeX(boundaryPanel.getElement());
				int mouseY = event.getRelativeY(boundaryPanel.getElement());
				Location mouseLoc = new CoordinateLocation(mouseX, mouseY);
				// System.out.println("mouse=" + mouseLoc);
				Widget w = null;
				// for (Widget each : registered) {
				// if (each.getParent() != boundaryPanel) {
				// System.out.println("not a child of boundaryPanel="
				// + each);
				// continue;
				// }
				// int x = boundaryPanel.getWidgetLeft(each);
				// int y = boundaryPanel.getWidgetTop(each);
				// int width = each.getOffsetWidth();
				// int height = each.getOffsetHeight();
				// Area area = new CoordinateArea(x, y, x + width, y + height);
				// if (area.intersects(mouseLoc)) {
				// w = each;
				// }
				// }

				int len = boundaryPanel.getWidgetCount();
				for (int i = 0; i < len; i++) {
					Widget each = boundaryPanel.getWidget(i);
					int x = boundaryPanel.getWidgetLeft(each);
					int y = boundaryPanel.getWidgetTop(each);
					int width = each.getOffsetWidth();
					int height = each.getOffsetHeight();
					Area area = new CoordinateArea(x, y, x + width, y + height);
					if (area.intersects(mouseLoc)) {
						w = each;
					}
				}

				// System.out.println("widget=" + w);
				if (w == null) {
					pickupDragController.clearSelection();
				}
			}
		}, MouseDownEvent.getType());
		// boundaryPanel.sinkEvents(eventBitsToAdd);

		// windowController.getPickupDragController().addDragHandler(new
		// DemoDragHandler(new HTML()));

		// final HTML eventTextArea = new HTML();
		// eventTextArea.addStyleName("demo-event-text-area");
		// eventTextArea.setSize("800px", "800px");
		//
		// final DemoDragHandler demoDragHandler = new DemoDragHandler(
		// eventTextArea);
		// dragController.addDragHandler(demoDragHandler);

		//
		// boundaryPanel.add(new WindowExample(demoDragHandler));

		// AbsolutePanel panel = new AbsolutePanel();
		// Button panel = new Button("aa");
		// HTMLPanel panel = new HTMLPanel("<html></html>");
		// AbsolutePanel panel = new AbsolutePanel();
		AbsolutePanel panel = new AbsolutePanel();
		panel.setSize("100px", "27px");
		panel.getElement().getStyle().setProperty("borderStyle", "solid");
		panel.getElement().getStyle().setProperty("borderWidth", "1px");
		panel.getElement().getStyle().setProperty("borderColor", "red");
		// panel.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		boundaryPanel.add(panel, 30, 30);

		HTML dragpart = new HTML();
		dragpart.setSize("100px", "27px");
		dragpart.getElement().getStyle().setProperty("backgroundColor", "gray");
		panel.add(dragpart, 0, 0);

		HTML onlabel = new HTML("<a>on panel</a>");
		onlabel.getElement().getStyle().setProperty("cursor", "pointer");
		panel.add(onlabel, 20, 0);

		onlabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("label clicked");
				// openWindow(pickupDragController);
			}
		});

		panel.addDomHandler(new ContextMenuHandler() {
			public void onContextMenu(ContextMenuEvent event) {
				int x = event.getNativeEvent().getClientX();
				int y = event.getNativeEvent().getClientY();

				MenuBar popupMenuBar = new MenuBar(true);
				// System.out.println("A");
				event.preventDefault();
				// event.stopPropagation();
				final PopupPanel popup = new PopupPanel(true);
				popup.add(popupMenuBar);
				popupMenuBar.addItem(new MenuItem("test1",
						new ScheduledCommand() {
							public void execute() {
								System.out.println("test1 pressed");
								popup.hide(true);
							};
						}));
				popupMenuBar.addItem(new MenuItem("test2",
						new ScheduledCommand() {
							public void execute() {
								System.out.println("test2 pressed");
								popup.hide(true);
							};
						}));
				// popupMenuBar.addItem(new MenuItem("test2"));
				// popup.add(new Button("Test2"));
				popup.setPopupPosition(x, y);
				popup.show();
			}

		}, ContextMenuEvent.getType());
		// onlabel.getElement().add

		// //dragController.makeDraggable(panel, onlabel);
		// onlabel.addDoubleClickHandler(new DoubleClickHandler() {
		//
		// @Override
		// public void onDoubleClick(DoubleClickEvent event) {
		// // TODO Auto-generated method stub
		// System.out.println("double click");
		// }
		// });

		// pickupDragController.makeDraggable(panel, dragpart);
		onlabel.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("AAA");
			}
		}, ClickEvent.getType());

		// onlabel.addMouseUpHandler(new MouseUpHandler() {
		//
		// @Override
		// public void onMouseUp(MouseUpEvent event) {
		// int button = event.getNativeButton();
		// if (button == NativeEvent.BUTTON_LEFT) {
		// event.preventDefault();
		// event.stopPropagation();
		// openWindow(pickupDragController);
		// }
		// }
		// });

		pickupDragController.makeDraggable(panel, onlabel);

		class A implements MouseUpHandler, MouseDownHandler, MouseMoveHandler {
			int x;
			int y;

			@Override
			public void onMouseDown(MouseDownEvent event) {
				System.out.println("down");
				x = event.getClientX();
				y = event.getClientY();
				// event.stopPropagation();
				// click = true;
			}

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				// System.out.println("move");
				// click = false;
			}

			@Override
			public void onMouseUp(MouseUpEvent event) {
				System.out.println("up");
				boolean click = event.getClientX() == x
						&& event.getClientY() == y;
				System.out.println(click);
				int button = event.getNativeButton();
				if (button == NativeEvent.BUTTON_LEFT && click) {
					event.preventDefault();
					event.stopPropagation();
					openWindow(pickupDragController);
				}
			}
		}
		A mouseHandler = new A();
		onlabel.addMouseUpHandler(mouseHandler);
		onlabel.addMouseMoveHandler(mouseHandler);
		onlabel.addMouseDownHandler(mouseHandler);

		// onlabel.addMouseDownHandler(new MouseDownHandler() {
		//
		// @Override
		// public void onMouseDown(MouseDownEvent event) {
		// System.out.println("CD");
		// }
		// });

		// droppanel test
		// DropPanel dropPanel = new DropPanel();
		// dropPanel.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		// dropPanel.getElement().getStyle().setBorderWidth(2, Unit.PX);
		// dropPanel.setSize("100px", "100px");
		// boundaryPanel.add(dropPanel, 0, 100);
		// dropPanel.addDropHandler(new DropHandler() {
		//
		// @Override
		// public void onDrop(DropEvent event) {
		// System.out.println("eat!");
		// System.out
		// .println(event.getDataTransfer().getClass().getName());
		// DataTransferExt ext = (DataTransferExt) event.getDataTransfer();
		// System.out.println(ext);
		// System.out.println(ext.getTypes());
		// }
		// });

		Label label = new Label("hoge");
		// panel.add(label);
		// boundaryPanel.add(panel, 100, 100);
		pickupDragController.makeDraggable(label);
		// label.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		boundaryPanel.add(label, 100, 100);
		// label.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// // TODO Auto-generated method stub
		// System.out.println("clicked(3)");
		// }
		// });

		// demoDragHandler.
		//Widget w = DraggableFactory.createDraggableRedBox(pickupDragController);
		//boundaryPanel.add(w, 100, 50);

		HTML html = new HTML("<html><body>hoge</body></html>");
		html.setSize("100px", "27px");
		html.getElement().getStyle().setProperty("borderStyle", "solid");
		html.getElement().getStyle().setProperty("borderWidth", "1px");
		html.getElement().getStyle().setProperty("borderColor", "red");
		// html.
		// html.addAttachHandler(handler)
		pickupDragController.makeDraggable(html);
		boundaryPanel.add(html, 200, 200);
	}

	protected void openWindow(PickupDragController pickupDragController) {
		openWindow3(pickupDragController);
	}

	protected void openWindow3(PickupDragController pickupDragController) {
		HTML html1 = new HTML(makeText().replaceAll("\n", "<br>\n"));
		html1.addStyleName("demo-resize-html");
		WindowPanel windowPanel1 = new WindowPanel(windowController,
				"abcde this is title", html1, true, pickupDragController);
		boundaryPanel.add(windowPanel1, 20, 20);
	}

	protected void openWindow2(PickupDragController pickupDragController) {

		FocusPanel header1 = new FocusPanel();
		Grid grid = new Grid(1, 2);
		header1.add(grid);

		Label l = new Label("long title long title ");
		l.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		grid.setWidget(0, 0, l);

		Button b = new Button("x");
		b.setSize("23px", "23px");
		grid.setWidget(0, 1, b);
		grid.getColumnFormatter().setWidth(0, "100%");

		HTML html1 = new HTML(makeText().replaceAll("\n", "<br>\n"));
		html1.addStyleName("demo-resize-html");
		WindowPanel windowPanel1 = new WindowPanel(windowController, header1,
				html1, true, pickupDragController);
		boundaryPanel.add(windowPanel1, 20, 20);
	}

	protected void openWindow1(PickupDragController pickupDragController) {
		// System.out.println("clicked");

		// create the first panel

		// HTML header1 = new HTML("An draggable &amp; resizable panel");
		// header1.add
		FocusPanel header1 = new FocusPanel();
		HTMLPanel headerContent = new HTMLPanel("");
		// headerContent.add(new Label("You can resize "));
		// headerContent.add(Element.create
		// FlowPa
		InlineLabel title = new InlineLabel(
				"This is title This is title This is title This is title This is title");
		headerContent.add(title);
		title.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		Button b = new Button("x");
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("button clicked");
			}
		});
		headerContent.setWidth("200px");
		// headerContent.setHeight("27px");
		b.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {

				System.out.println("mouse down");
			}
		});
		headerContent.add(b);
		// b.getElement().getStyle().setRight(0, Unit.PT);
		// b.getElement().setAttribute("style", "float: right;");
		b.getElement().getStyle().setFloat(Float.RIGHT);
		header1.add(headerContent);
		HTML html1 = new HTML(makeText().replaceAll("\n", "<br>\n"));
		html1.addStyleName("demo-resize-html");
		WindowPanel windowPanel1 = new WindowPanel(windowController, header1,
				html1, true, pickupDragController);
		boundaryPanel.add(windowPanel1, 20, 20);
	}

	private String makeText() {
		String t = "You can resize this panel by any of the four edges or corners.\n";
		for (int i = 0; i < 3; i++) {
			t += "The quick brown fox jumped over the lazy dog.\n";
		}
		return t;
	}

}
