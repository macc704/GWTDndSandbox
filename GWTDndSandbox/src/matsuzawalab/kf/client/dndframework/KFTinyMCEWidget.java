package matsuzawalab.kf.client.dndframework;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * TinyMCE -
 * 
 * A wrapper widget for using TinyMCE. It contains a number of JSNI methods that
 * I have found useful during development
 * 
 * @author Aaron Watkins
 */
public class KFTinyMCEWidget extends SimplePanel {

	private HTML htmlWidget;
	private String id;

	private IKFEditorChangeHandler changeHandler;

	public KFTinyMCEWidget(String initialContent) {
		id = HTMLPanel.createUniqueId();
		htmlWidget = new HTML();
		// htmlWidget.setText(initialContent);
		htmlWidget.setHTML(initialContent);
		htmlWidget.getElement().setId(id);
		setWidget(htmlWidget);

		this.addAttachHandler(new AttachEvent.Handler() {
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				if (!event.isAttached()) {// detach
					removeMCE(id);
				} else {
					init(id, KFTinyMCEWidget.this);
					System.out.println(isDirty(id));
				}
			}
		});
	}

	public void setChangeHandler(IKFEditorChangeHandler changeHandler) {
		this.changeHandler = changeHandler;
	}

	/**
	 * setText() -
	 * 
	 * NOTE:
	 * 
	 * @param text
	 */
	public void setText(String text) {
		// htmlWidget.setText(text);
		updateContent(id, text);
	}

	public String getText() {
		save();
		return htmlWidget.getText();
	}

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		super.onLoad();
	}

	/**
	 * focusMCE() -
	 * 
	 * Use this to set the focus to the MCE area
	 * 
	 * @param id
	 *            - the element's ID
	 */
	protected native void focusMCE(String id) /*-{
		$wnd.tinyMCE.execCommand('mceFocus', true, id);
	}-*/;

	/**
	 * resetMCE() -
	 * 
	 * Use this if reusing the same MCE element, but losing focus
	 */
	protected native void resetMCE() /*-{
		$wnd.tinyMCE.execCommand('mceResetDesignMode', true);
	}-*/;

	/**
	 * unload() -
	 * 
	 * Unload this MCE editor instance from active memory. I use this in the
	 * onHide function of the containing widget. This helps to avoid problems,
	 * especially when using tabs.
	 */
	public void unload() {
		unloadMCE(id);
	}

	/**
	 * unloadMCE() -
	 * 
	 * @param id
	 *            - The element's ID JSNI method to implement unloading the MCE
	 *            editor instance from memory
	 */
	protected native void unloadMCE(String id) /*-{
		$wnd.tinyMCE.execCommand('mceRemoveControl', false, id);
	}-*/;

	/**
	 * updateContent() -
	 * 
	 * Update the internal referenced content. Use this if you programatically
	 * change the original text area's content (eg. do a clear)
	 * 
	 * @param id
	 *            - the ID of the text area that contains the content you wish
	 *            to copy
	 */
	protected native void updateContent(String id, String content) /*-{
		$wnd.tinyMCE.get(id).setContent(content);
	}-*/;

	/**
	 * getTextArea() -
	 * 
	 */
	protected native void save() /*-{
		$wnd.tinyMCE.triggerSave();
	}-*/;

	/**
	 * removeMCE() -
	 * 
	 * Remove a tiny MCE editing field from a text area
	 * 
	 * @param id
	 *            - the text area's ID
	 */
	protected native void removeMCE(String id) /*-{
		$wnd.tinyMCE.get(id).remove();
	}-*/;

	protected native void init(String id, KFTinyMCEWidget w) /*-{
		var selectorid = '#' + id;
		$wnd.tinyMCE
				.init({
					selector : selectorid,
					setup : function(ed) {
						ed.on('dragstart', function(e) {
							var text = e.dataTransfer.getData("text/plain");
							e.dataTransfer.setData("special", "\"" + text
									+ "\"");
						});
						ed
								.on(
										'drop',
										function(e) {
											var text = e.dataTransfer
													.getData("special");
											if (text == null
													|| text.length == 0) {
												return;
											}

											e.stopPropagation();
											e.preventDefault();

											var doc = ed.getDoc(), urlPrefix = 'data:text/mce-internal,';
											var pointRng = doc
													.caretRangeFromPoint(e.x,
															e.y);
											ed.selection.setRng(pointRng);
											ed.insertContent(text);
										});
						ed
								.on(
										'change',
										function(e) {
											w.@matsuzawalab.kf.client.dndframework.KFTinyMCEWidget::changed(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
										});
					}
				});
	}-*/;

	public boolean isDirty() {
		return isDirty(id);
	}

	public void changed(JavaScriptObject jsObject) {
		// NativeEvent ev = (NativeEvent) jsObject;
		if (changeHandler != null) {
			changeHandler.stateChanged();
		}
	}

	protected native boolean isDirty(String id) /*-{
		return $wnd.tinyMCE.get(id).isDirty();
	}-*/;

}
