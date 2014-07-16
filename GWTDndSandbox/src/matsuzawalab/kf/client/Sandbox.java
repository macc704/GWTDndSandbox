package matsuzawalab.kf.client;

import matsuzawalab.kf.client.dndframework.IKExternalObjectDropHandler;
import matsuzawalab.kf.client.dndframework.KFDataTransfer;
import matsuzawalab.kf.client.dndframework.KFExternalObjectDropController;
import matsuzawalab.kf.client.dndframework.KFPickupDragController;
import matsuzawalab.kf.client.dndframework.KFSelectionManager;
import matsuzawalab.kf.client.dndframework.KFWindowController;

import org.vectomatic.file.File;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sandbox implements EntryPoint {

	private KFPickupDragController pickupDragController;
	private KFWindowController windowController;

	public void onModuleLoad() {
		RootPanel mainPanel = RootPanel.get("mainpanel");
		mainPanel.getElement().setInnerHTML("hello! dnd demo!");

		AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.addStyleName("demo-WindowExample");
		boundaryPanel.addStyleName("demo-main-boundary-panel");
		boundaryPanel.setPixelSize(600, 400);
		mainPanel.add(boundaryPanel);

		pickupDragController = new KFPickupDragController(boundaryPanel);
		KFSelectionManager selectionManager = new KFSelectionManager();
		selectionManager.makeMultipleSelection(pickupDragController);
		windowController = new KFWindowController(boundaryPanel);

		addLabel("hoge1", makeText(), 100, 100);
		addLabel("hoge2", makeText(), 200, 200);
		addLabel("hoge3", makeText(), 300, 300);

		initializeExternalDrop(boundaryPanel);
	}

	private void initializeExternalDrop(AbsolutePanel boundaryPanel) {
		KFExternalObjectDropController exDropHandler = new KFExternalObjectDropController();
		exDropHandler.setDroppable(boundaryPanel,
				new IKExternalObjectDropHandler() {

					@Override
					public boolean isAcceptable(KFDataTransfer dataTransfer) {
						return dataTransfer.hasFiles("text/plain")
								|| dataTransfer.hasFiles("application/pdf");
					}

					@Override
					public void dropped(KFDataTransfer dataTransfer,
							final int x, final int y) {
						if (dataTransfer.hasUrllist()) {
							addLabel("URL", dataTransfer.getUrllist(), x, y);
						}

						if (dataTransfer.hasFiles("text/plain")) {
							for (final File f : dataTransfer.getFiles()) {
								final FileReader reader = new FileReader();
								reader.addLoadEndHandler(new LoadEndHandler() {
									@Override
									public void onLoadEnd(LoadEndEvent event) {
										String name = f.getName();
										String text = reader.getStringResult();
										addLabel(name, text, x, y);
									}
								});
								reader.readAsText(f, "UTF-8");
							}
						}

						if (dataTransfer.hasFiles("application/pdf")) {
							for (File f : dataTransfer.getFiles()) {
								System.out.println(f.getName());
								System.out.println(f.getType());
							}
						}
					}
				});
	}

	public void addLabel(String title, String content, int x, int y) {
		AbsolutePanel boundaryPanel = pickupDragController.getBoundaryPanel();
		SampleLabel label = new SampleLabel(title, content);
		label.bindEvents(windowController, pickupDragController);
		boundaryPanel.add(label, x, y);
	}

	private String makeText() {
		String t = "You can resize this panel by any of the four edges or corners.\n";
		for (int i = 0; i < 3; i++) {
			t += "The quick brown fox jumped over the lazy dog.\n";
		}
		return t;
	}

}
