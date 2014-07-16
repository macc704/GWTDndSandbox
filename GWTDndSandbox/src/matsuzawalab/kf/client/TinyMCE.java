package matsuzawalab.kf.client;

import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * TinyMCE -
 * 
 * A wrapper widget for using TinyMCE. It contains a number of JSNI methods that
 * I have found useful during development
 * 
 * @author Aaron Watkins
 */
public class TinyMCE extends SimplePanel {

	private TextArea ta;
	private String id;

	public TinyMCE() {
		initTinyMCE();
		// initWidget(initTinyMCE());
	}

	private VerticalPanel initTinyMCE() {
		// int width = 65;
		// int height = 30;
		// VerticalPanel panel = new VerticalPanel();
		// panel.setWidth("100%");
		addHandler(new DragStartHandler() {

			@Override
			public void onDragStart(DragStartEvent event) {
				// TODO Auto-generated method stub
				System.out.println("drag start!");
			}
		}, DragStartEvent.getType());
		sinkEvents(Event.DRAGDROP);

		id = HTMLPanel.createUniqueId();
		ta = new TextArea();
		// ta.setCharacterWidth(width);
		// ta.setVisibleLines(height);
		ta.getElement().setId(id);
		ta.setText("hogehoge");
		ta.addDragStartHandler(new DragStartHandler() {

			@Override
			public void onDragStart(DragStartEvent event) {
				System.out.println("drag start2!");
			}
		});
		// DOM.setElementAttribute(ta.getElement(), "id", id);
		// DOM.setStyleAttribute(ta.getElement(), "width", "100%");
		// DOM.setStyleAttribute(ta.getElement(), "height", "100px");
		// DOM.setStyleAttribute(ta.getElement(), "height", "250px"); // delete
		// line?
		setWidget(ta);
		this.addAttachHandler(new AttachEvent.Handler() {

			@Override
			public void onAttachOrDetach(AttachEvent event) {
				// DOM.getElementById(DEBUG_ID_PREFIX)
				// System.out.println("aa" + event.isAttached());
				if (!event.isAttached()) {// detach

					// html = iframe().getContentDocument().getNodeValue();
					removeMCE(id);
					// // saved = getText();
					// html = iframe().getInnerHTML();
				} else {
					// if(html != null){
					// //iframe().getContentDocument().setNodeValue(html);
					// }
					init(id);
					// BodyElement body =
					// iframe().getContentDocument().getBody();

					// iframe().setInnerHTML("<html><body>this is body<body></html>");
					// // Frame frame = Frame.wrap(iframe());
					// System.out.println(iframe());
					// //System.out.println("attach" + iframe().getInnerHTML());
					// System.out.println("attach" +
					// iframe().getContentDocument().getBody());
					// System.out.println("attach" + ;
					// // frame.addHandler(new DragStartHandler() {
					//
					// @Override
					// public void onDragStart(DragStartEvent event) {
					// // TODO Auto-generated method stub
					// System.out.println("drag start3!");
					// }
					// }, DragStartEvent.getType());
					// frame.sinkEvents(Event.DRAGDROP);
					// atacch
					// if(iframe().getContentDocument() == null){
					// if(html != null){
					// System.out.println(html);
					// iframe().setInnerHTML(html);
					// }
					// }
				}
			}
		});

		// return panel;
		return null;
	}

	private String html;

	public IFrameElement iframe() {
		Element iframeElem = DOM.getElementById(id + "_ifr");
		IFrameElement iframe = (IFrameElement) iframeElem;
		return iframe;
	}

	private String saved;

	/**
	 * getID() -
	 * 
	 * @return the MCE element's ID
	 */
	public String getID() {
		return id;
	}

	/**
	 * setText() -
	 * 
	 * NOTE:
	 * 
	 * @param text
	 */
	public void setText(String text) {
		ta.setText(text);
		updateContent(id, text);
	}

	public String getText() {
		getTextData();
		return ta.getText();
	}

	boolean initialized = false;

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		super.onLoad();

		// System.out.println("onLoad");
		// init(id);
		// iframe().
		// DeferredCommand.addCommand(new Command() {
		// public void execute() {
		// setWidth("100%");
		// if (!initialized) {
		//
		// initialized = true;
		// }

		// System.out.println(getElement());
		// IFrameElement e = (IFrameElement) DOM.getElementById(id + "_ifr");
		// System.out.println(e);

		// NodeList<Element> elements =
		// getElement().getElementsByTagName("body");
		// int len = elements.getLength();
		// System.out.println(len);
		// for (int i = 0; i < len; i++) {
		// Element e = elements.getItem(i);
		// System.out.println(e);
		// }

		// initialized = true;
		// setText("hogera");
		// }

		// if (saved != null) {
		// // System.out.println(saved);
		// // setText(saved);
		// }
		// setTextAreaToTinyMCE(id);
		// focusMCE(id);
		// }
		// });
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
		//$wnd.tinyMCE.selectedInstance = $wnd.tinyMCE.getInstanceById(id);
		//$wnd.tinyMCE.setContent($wnd.document.getElementById(id).value);
		$wnd.console.log($wnd.tinyMCE.activeEditor);
		$wnd.tinyMCE.get(id).setContent(content);
	}-*/;

	/**
	 * getTextArea() -
	 * 
	 */
	protected native void getTextData() /*-{
		$wnd.tinyMCE.triggerSave();
	}-*/;

	/**
	 * encodeURIComponent() -
	 * 
	 * Wrapper for the native URL encoding methods
	 * 
	 * @param text
	 *            - the text to encode
	 * @return the encoded text
	 */
	protected native String encodeURIComponent(String text) /*-{
		return encodeURIComponent(text);
	}-*/;

	/**
	 * setTextAreaToTinyMCE() -
	 * 
	 * Change a text area to a tiny MCE editing field
	 * 
	 * @param id
	 *            - the text area's ID
	 */
	protected native void setTextAreaToTinyMCE(String id) /*-{
		$wnd.tinyMCE.execCommand('mceAddControl', true, id);
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
		//$wnd.tinyMCE.execCommand('mceRemoveControl', true, id);
		$wnd.tinyMCE.get(id).remove();
	}-*/;

	// public String initMode(){
	// return config.getMode();
	// }

	protected native void init(String id) /*-{
		//		$wnd.tinyMCE.init({
		//			selector : 'textarea',
		//			plugins : "save",
		//			toolbar : "save"
		//		});
		var selectorid = '#' + id;
		$wnd.tinyMCE.init({
			selector : selectorid,
			plugins : "save",
			toolbar : "save",
			setup : function(ed) {
				ed.on('dragstart', function(e) {
					var text = e.dataTransfer.getData("text/plain");					
					//e.dataTransfer.setData("text/plain", "\""+text+"\"");
					//var text = e.dataTransfer.getData("URL");
					//$wnd.console.log("dragstart"+text);
					//e.dataTransfer.setData("text/plain", "\""+text+"\"");									
					e.dataTransfer.setData("special", "\""+text+"\"");									
				});
				//var original = ed.off('drop');
				//$wnd.console.log(original);
				//ed.on('drop', original);			
				ed.on('drop', function(e) {
					var text = e.dataTransfer.getData("special");
					if(text == null || text.length == 0){
						return;
					}
										
					e.stopPropagation();
					e.preventDefault();
					
					var doc = ed.getDoc(), urlPrefix = 'data:text/mce-internal,';
					//var text = e.dataTransfer.getData("URL");
					
					//$wnd.console.log("drop"+text);
					//$wnd.console.log("drop"+e.dataTransfer.getData("text/plain"));
					//$wnd.console.log("drop"+e.dataTransfer.getData("text/html"));
					var pointRng = doc.caretRangeFromPoint(e.x, e.y);
					ed.selection.setRng(pointRng);
					ed.insertContent(text);
				});	
				
			}
		});
	}-*/;

	// /**
	// * TODO: use a configuration class to configure as like the editor (see
	// codemirror configuration or ask Sam)
	// * Initialize Tiny MCE editor
	// * http://wiki.moxiecode.com/index.php/TinyMCE:Configuration for details
	// */
	// protected native void init(AbstractTinyMCEConfiguration conf) /*-{
	// $wnd.tinyMCE.init({
	// // General options
	// mode : conf.@gr.open.client.AbstractTinyMCEConfiguration::getMode()(),
	// theme : conf.@gr.open.client.AbstractTinyMCEConfiguration::getTheme()(),
	// skin : conf.@gr.open.client.AbstractTinyMCEConfiguration::getSkin()(),
	// entity_encoding :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getEntityEncoding()(),
	// plugins :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getPlugins()(),
	//
	// // Theme options
	// // excluded buttons:
	// ,fontselect,fontsizeselect,preview,image,help,|,forecolor,backcolor
	// tablecontrols,|,,emotions,media,|,print
	// theme_advanced_buttons1 :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedButtons1()(),
	// theme_advanced_buttons2 :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedButtons2()(),
	// theme_advanced_buttons3 :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedButtons3()(),
	// //theme_advanced_buttons4 :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedButtons4()(),
	// theme_advanced_toolbar_location :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedToolbarLocation()(),
	// theme_advanced_toolbar_align :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedToolbarAlign()(),
	// theme_advanced_statusbar_location :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedStatusbarLocation()(),
	// theme_advanced_resizing :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedResizing()(),
	//
	// // Example content CSS (should be your site CSS)
	// content_css :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getContentCss()(),
	//
	// // Drop lists for link/image/media/template dialogs
	// template_external_list_url :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getTemplateExternalListUrl()(),
	// external_link_list_url :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getExternalLinkListUrl()(),
	// external_image_list_url :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getExternalImageListUrl()(),
	// media_external_list_url :
	// conf.@gr.open.client.AbstractTinyMCEConfiguration::getMediaExternalListUrl()(),
	//
	// // Replace values for the template plugin
	// template_replace_values : {
	// username : "Some User",
	// staffid : "991234"
	// }
	//
	// });
	//
	//
	// }-*/;
	//
	//
	// public AbstractTinyMCEConfiguration getConfig() {
	// return config;
	// }
}

//tinymce default code
//editor.on('dragstart', function(e) {
//	var selectionHtml;
//
//	if (editor.selection.isCollapsed() && e.target.tagName == 'IMG') {
//		selection.select(e.target);
//	}
//
//	dragStartRng = selection.getRng();
//	selectionHtml = editor.selection.getContent();
//
//	// Safari doesn't support custom dataTransfer items so we can only use URL and Text
//	if (selectionHtml.length > 0) {
//		e.dataTransfer.setData('URL', 'data:text/mce-internal,' + escape(selectionHtml));
//	}
//});
//
//editor.on('drop', function(e) {
//	if (!isDefaultPrevented(e)) {
//		var internalContent = e.dataTransfer.getData('URL');
//
//		if (!internalContent || internalContent.indexOf(urlPrefix) == -1 || !doc.caretRangeFromPoint) {
//			return;
//		}
//
//		internalContent = unescape(internalContent.substr(urlPrefix.length));
//		if (doc.caretRangeFromPoint) {
//			e.preventDefault();
//
//			// Safari has a weird issue where drag/dropping images sometimes
//			// produces a green plus icon. When this happens the caretRangeFromPoint
//			// will return "null" even though the x, y coordinate is correct.
//			// But if we detach the insert from the drop event we will get a proper range
//			window.setTimeout(function() {
//				var pointRng = doc.caretRangeFromPoint(e.x, e.y);
//
//				if (dragStartRng) {
//					selection.setRng(dragStartRng);
//					dragStartRng = null;
//				}
//
//				customDelete();
//
//				selection.setRng(pointRng);
//				editor.insertContent(internalContent);
//			}, 0);
//		}
//
//	}
//});
