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
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
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

	private static final int BORDER_THICKNESS = 4;

	private static final String CSS_DEMO_RESIZE_EDGE = "demo-resize-edge";

	private static final String CSS_DEMO_RESIZE_PANEL = "demo-WindowPanel";

	private static final String CSS_DEMO_RESIZE_PANEL_HEADER = "demo-WindowPanel-header";

	private int contentHeight;

	private Widget contentOrScrollPanelWidget;

	private int contentWidth;

	private Widget eastWidget;

	private Grid grid = new Grid(3, 3);

	private FocusPanel headerContainer;

	private Widget headerWidget;

	private boolean initialLoad = false;

	private Widget northWidget;

	private Widget southWidget;

	private Widget westWidget;

	private WindowController windowController;

	public WindowPanel(WindowController windowController, Widget headerWidget,
			Widget contentWidget, boolean wrapContentInScrollPanel,
			final PickupDragController pickupDragController) {
		throw new RuntimeException("not implemented");
	}

	public WindowPanel(WindowController windowController, String title,
			Widget contentWidget, boolean wrapContentInScrollPanel,
			final PickupDragController pickupDragController) {

		final DropController drop = new SimpleDropController(this) {
			@Override
			public void onDrop(DragContext context) {
				System.out.println("drop=" + context.draggable);
			}

			@Override
			public void onEnter(DragContext context) {
				getDropTarget().addStyleName("dragdrop-droptarget");
				super.onEnter(context);
			}

			@Override
			public void onLeave(DragContext context) {
				getDropTarget().removeStyleName("dragdrop-droptarget");
				super.onLeave(context);
			}

			@Override
			public void onPreviewDrop(DragContext context)
					throws VetoDragException {
				// throw new VetoDragException();
			}
		};

		Grid grid = new Grid(1, 2);
		this.headerWidget = grid;

		Label l = new Label("long title long title ");
		l.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		grid.setWidget(0, 0, l);

		Button b = new Button("x");
		b.setSize("23px", "23px");
		grid.setWidget(0, 1, b);
		grid.getColumnFormatter().setWidth(0, "100%");

		b.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.stopPropagation();
			}
		});

		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				WindowPanel.this.removeFromParent();
				pickupDragController.unregisterDropController(drop);
			}
		});

		pickupDragController.registerDropController(drop);

		initialize(windowController, grid, contentWidget,
				wrapContentInScrollPanel);
	}

	public WindowPanel(WindowController windowController, Widget headerWidget,
			Widget contentWidget, boolean wrapContentInScrollPanel) {
		initialize(windowController, headerWidget, contentWidget,
				wrapContentInScrollPanel);
	}

	private void initialize(final WindowController windowController,
			Widget headerWidget, Widget contentWidget,
			boolean wrapContentInScrollPanel) {
		this.windowController = windowController;
		this.headerWidget = headerWidget;
		addStyleName(CSS_DEMO_RESIZE_PANEL);

		contentOrScrollPanelWidget = wrapContentInScrollPanel ? new ScrollPanel(
				contentWidget) : contentWidget;

		headerContainer = new FocusPanel();
		headerContainer.addStyleName(CSS_DEMO_RESIZE_PANEL_HEADER);
		headerContainer.add(headerWidget);

		windowController.getPickupDragController().makeDraggable(this,
				headerContainer);

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

		// System.out.println("resize!" + width + "," + height);
		width = width > 20 ? width : 20;
		headerWidget.setSize(width + "px", "27px");
		headerWidget.setLayoutData(null);
	}

	private Widget setupCell(int row, int col, DirectionConstant direction) {
		final FocusPanel widget = new FocusPanel();
		// final SimplePanel widget = new SimplePanel();
		// final HTMLPanel widget = new HTMLPanel("");
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
