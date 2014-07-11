package matsuzawalab.kf.client;

import matsuzawalab.kf.client.a.WindowController;
import matsuzawalab.kf.client.b.KFLabel;
import matsuzawalab.kf.client.b.KFSelectionManager;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sandbox implements EntryPoint {

	private PickupDragController pickupDragController;
	private WindowController windowController;

	public void onModuleLoad() {
		RootPanel mainPanel = RootPanel.get("mainpanel");
		mainPanel.getElement().setInnerHTML("hello! dnd demo!");

		AbsolutePanel boundaryBasePanel = new AbsolutePanel();
		boundaryBasePanel.addStyleName("demo-main-boundary-panel");
		mainPanel.add(boundaryBasePanel);

		SimplePanel panel = new SimplePanel();
		panel.addStyleName("demo-WindowExample");
		boundaryBasePanel.add(panel);

		AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.addStyleName("demo-WindowExample");
		boundaryPanel.setPixelSize(600, 400);
		panel.add(boundaryPanel);

		pickupDragController = new PickupDragController(boundaryPanel, true);
		KFSelectionManager selectionManager = new KFSelectionManager();
		selectionManager.makeMultipleSelection(pickupDragController);

		windowController = new WindowController(boundaryPanel);

		HTML html = new HTML("AA");
		pickupDragController.makeDraggable(html);
		boundaryPanel.add(html, 20,20);
		addLabel("hoge1", 100, 100);
		addLabel("hoge2", 200, 200);
		addLabel("hoge3", 300, 300);
	}

	public void addLabel(String title, int x, int y) {
		AbsolutePanel boundaryPanel = pickupDragController.getBoundaryPanel();
		KFLabel label = new KFLabel(title);
		label.bindEvents(windowController, pickupDragController);
		boundaryPanel.add(label, x, y);
	}

}
