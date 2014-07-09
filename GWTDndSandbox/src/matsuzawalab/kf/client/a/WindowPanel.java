/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package matsuzawalab.kf.client.a;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

final public class WindowPanel extends FocusPanel {

	/**
	 * WindowPanel direction constant, used in
	 * {@link ResizeDragController#makeDraggable(Widget, DirectionConstant)}.
	 */
	public static class DirectionConstant {

		public final int directionBits;

		public final String directionLetters;

		private DirectionConstant(int directionBits, String directionLetters) {
			this.directionBits = directionBits;
			this.directionLetters = directionLetters;
		}
	}

	/**
	 * Specifies that resizing occur at the east edge.
	 */
	public static final int DIRECTION_EAST = 0x0001;

	/**
	 * Specifies that resizing occur at the both edge.
	 */
	public static final int DIRECTION_NORTH = 0x0002;

	/**
	 * Specifies that resizing occur at the south edge.
	 */
	public static final int DIRECTION_SOUTH = 0x0004;

	/**
	 * Specifies that resizing occur at the west edge.
	 */
	public static final int DIRECTION_WEST = 0x0008;

	/**
	 * Specifies that resizing occur at the east edge.
	 */
	public static final DirectionConstant EAST = new DirectionConstant(
			DIRECTION_EAST, "e");

	/**
	 * Specifies that resizing occur at the both edge.
	 */
	public static final DirectionConstant NORTH = new DirectionConstant(
			DIRECTION_NORTH, "n");

	/**
	 * Specifies that resizing occur at the north-east edge.
	 */
	public static final DirectionConstant NORTH_EAST = new DirectionConstant(
			DIRECTION_NORTH | DIRECTION_EAST, "ne");

	/**
	 * Specifies that resizing occur at the north-west edge.
	 */
	public static final DirectionConstant NORTH_WEST = new DirectionConstant(
			DIRECTION_NORTH | DIRECTION_WEST, "nw");

	/**
	 * Specifies that resizing occur at the south edge.
	 */
	public static final DirectionConstant SOUTH = new DirectionConstant(
			DIRECTION_SOUTH, "s");

	/**
	 * Specifies that resizing occur at the south-east edge.
	 */
	public static final DirectionConstant SOUTH_EAST = new DirectionConstant(
			DIRECTION_SOUTH | DIRECTION_EAST, "se");

	/**
	 * Specifies that resizing occur at the south-west edge.
	 */
	public static final DirectionConstant SOUTH_WEST = new DirectionConstant(
			DIRECTION_SOUTH | DIRECTION_WEST, "sw");

	/**
	 * Specifies that resizing occur at the west edge.
	 */
	public static final DirectionConstant WEST = new DirectionConstant(
			DIRECTION_WEST, "w");

	private static final int BORDER_THICKNESS = 5;

	private static final String CSS_DEMO_RESIZE_EDGE = "demo-resize-edge";

	private static final String CSS_DEMO_RESIZE_PANEL = "demo-WindowPanel";

	private static final String CSS_DEMO_RESIZE_PANEL_HEADER = "demo-WindowPanel-header";

	private int contentHeight;

	private Widget contentOrScrollPanelWidget;

	private int contentWidth;

	private Widget eastWidget;

	private Grid grid = new Grid(3, 3);

	private final FocusPanel headerContainer;

	private Widget headerWidget;

	private boolean initialLoad = false;

	private Widget northWidget;

	private Widget southWidget;

	private Widget westWidget;

	private final WindowController windowController;

	public WindowPanel(final WindowController windowController,
			Widget headerWidget, Widget contentWidget,
			boolean wrapContentInScrollPanel,
			PickupDragController pickupDragController) {
		this(windowController, headerWidget, contentWidget,
				wrapContentInScrollPanel);

		SimpleDropController drop = new SimpleDropController(this) {
			@Override
			public void onDrop(DragContext context) {
				System.out.println("drop");
			}

			@Override
			public void onEnter(DragContext context) {
				getDropTarget().getElement().getStyle()
						.setBorderStyle(BorderStyle.SOLID);
				getDropTarget().getElement().getStyle()
						.setBorderWidth(3, Unit.PT);
				getDropTarget().getElement().getStyle().setBorderColor("red");
				getDropTarget().getElement().getStyle()
						.setCursor(Cursor.CROSSHAIR);
				super.onEnter(context);
			}

			@Override
			public void onLeave(DragContext context) {
				getDropTarget().getElement().getStyle().clearBorderColor();
				getDropTarget().getElement().getStyle().clearBorderWidth();
				getDropTarget().getElement().getStyle().clearBorderStyle();
				getDropTarget().getElement().getStyle().clearCursor();
				super.onLeave(context);
			}

			@Override
			public void onPreviewDrop(DragContext context)
					throws VetoDragException {
//				DialogBox dialog = new DialogBox();
//				dialog.setModal(true);
//				dialog.setWidget(new Button("xx"));
//				dialog.setSize("100px", "100px");
//				dialog.show();
				throw new VetoDragException();
			}
		};
		pickupDragController.registerDropController(drop);
	}

	public WindowPanel(final WindowController windowController,
			Widget headerWidget, Widget contentWidget,
			boolean wrapContentInScrollPanel) {
		this.windowController = windowController;
		this.headerWidget = headerWidget;
		addStyleName(CSS_DEMO_RESIZE_PANEL);

		contentOrScrollPanelWidget = wrapContentInScrollPanel ? new ScrollPanel(
				contentWidget) : contentWidget;

		headerContainer = new FocusPanel();
		headerContainer.addStyleName(CSS_DEMO_RESIZE_PANEL_HEADER);

		// // AbsolutePanel header = new AbsolutePanel();
		FlowPanel header = new FlowPanel();
		this.headerWidget = header;
		// headerWidget = new HTML("ahoge");
		header.add(headerWidget);
		Button b = new Button("x");
		header.add(b);
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("window close button clicked");
				WindowPanel.this.removeFromParent();
			}
		});
		// Label label = new Label("abc");
		// label.setSize("100px", "27px");

		headerContainer.add(this.headerWidget);

		// windowController.getPickupDragController().makeDraggable(this,headerContainer);
		windowController.getPickupDragController().makeDraggable(this,
				headerWidget);

		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AbsolutePanel boundaryPanel = windowController
						.getBoundaryPanel();
				if (boundaryPanel.getWidgetIndex(WindowPanel.this) < boundaryPanel
						.getWidgetCount() - 1) {
					// force our panel to the top of our z-index context
					WidgetLocation location = new WidgetLocation(
							WindowPanel.this, boundaryPanel);
					boundaryPanel.add(WindowPanel.this, location.getLeft(),
							location.getTop());
				}
			}
		});

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(headerContainer);
		verticalPanel.add(contentOrScrollPanelWidget);

		grid.setCellSpacing(0);
		grid.setCellPadding(0);
		add(grid);

		setupCell(0, 0, NORTH_WEST);
		northWidget = setupCell(0, 1, NORTH);
		setupCell(0, 2, NORTH_EAST);

		westWidget = setupCell(1, 0, WEST);
		grid.setWidget(1, 1, verticalPanel);
		eastWidget = setupCell(1, 2, EAST);

		setupCell(2, 0, SOUTH_WEST);
		southWidget = setupCell(2, 1, SOUTH);
		setupCell(2, 2, SOUTH_EAST);

	}

	public int getContentHeight() {
		return contentHeight;
	}

	public int getContentWidth() {
		return contentWidth;
	}

	public void moveBy(int right, int down) {
		AbsolutePanel parent = (AbsolutePanel) getParent();
		Location location = new WidgetLocation(this, parent);
		int left = location.getLeft() + right;
		int top = location.getTop() + down;
		parent.setWidgetPosition(this, left, top);
	}

	public void setContentSize(int width, int height) {
		if (width != contentWidth) {
			contentWidth = width;
			headerContainer.setPixelSize(contentWidth,
					headerWidget.getOffsetHeight());
			northWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
			southWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
		}
		if (height != contentHeight) {
			contentHeight = height;
			int headerHeight = headerContainer.getOffsetHeight();
			westWidget.setPixelSize(BORDER_THICKNESS, contentHeight
					+ headerHeight);
			eastWidget.setPixelSize(BORDER_THICKNESS, contentHeight
					+ headerHeight);
		}
		contentOrScrollPanelWidget.setPixelSize(contentWidth, contentHeight);
	}

	private Widget setupCell(int row, int col, DirectionConstant direction) {
		final FocusPanel widget = new FocusPanel();
		widget.setPixelSize(BORDER_THICKNESS, BORDER_THICKNESS);
		grid.setWidget(row, col, widget);
		windowController.getResizeDragController().makeDraggable(widget,
				direction);
		grid.getCellFormatter().addStyleName(
				row,
				col,
				CSS_DEMO_RESIZE_EDGE + " demo-resize-"
						+ direction.directionLetters);
		return widget;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		if (!initialLoad && contentOrScrollPanelWidget.getOffsetHeight() != 0) {
			initialLoad = true;
			headerWidget.setPixelSize(headerWidget.getOffsetWidth(),
					headerWidget.getOffsetHeight());
			setContentSize(contentOrScrollPanelWidget.getOffsetWidth(),
					contentOrScrollPanelWidget.getOffsetHeight());
		}
	}
}
