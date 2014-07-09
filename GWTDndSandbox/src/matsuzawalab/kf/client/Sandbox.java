package matsuzawalab.kf.client;

import matsuzawalab.kf.client.a.DemoDragHandler;
import matsuzawalab.kf.client.a.DraggableFactory;
import matsuzawalab.kf.client.a.WindowController;
import matsuzawalab.kf.client.a.WindowExample;
import matsuzawalab.kf.client.a.WindowPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sandbox implements EntryPoint {
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
		onModuleLoad2();
	}

	public void onModuleLoad1() {
		// PickupDragController dragController = new PickupDragController(
		// boundaryPanel, true);
		// dragController.setBehaviorMultipleSelection(false);
		//
		WindowExample example = new WindowExample(new DemoDragHandler(
				new HTML()));
		boundaryBasePanel.add(example);
	}

	public void onModuleLoad2() {

		// instantiate the common drag controller used the less specific
		// examples
		final PickupDragController pickupDragController = new PickupDragController(
				boundaryPanel, true);
		pickupDragController.setBehaviorMultipleSelection(false);

		windowController = new WindowController(boundaryPanel);
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
				openWindow(pickupDragController);
			}
		});

		// //dragController.makeDraggable(panel, onlabel);
		// onlabel.addDoubleClickHandler(new DoubleClickHandler() {
		//
		// @Override
		// public void onDoubleClick(DoubleClickEvent event) {
		// // TODO Auto-generated method stub
		// System.out.println("double click");
		// }
		// });

		pickupDragController.makeDraggable(panel, dragpart);

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
		Widget w = DraggableFactory.createDraggableRedBox(pickupDragController);
		boundaryPanel.add(w, 100, 50);

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
		//System.out.println("clicked");

		// create the first panel
		HTML header1 = new HTML("An draggable &amp; resizable panel");
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
