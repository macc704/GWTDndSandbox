package matsuzawalab.kf.client.b;

import java.util.ArrayList;
import java.util.List;

import org.vectomatic.dnd.DataTransferExt;
import org.vectomatic.file.File;
import org.vectomatic.file.FileList;

import com.google.gwt.dom.client.DataTransfer;

public class KFDataTransfer {

	private DataTransfer dataTransfer;
	private List<File> files = new ArrayList<File>();
	private String urllist;
	private String text;
	private String html;

	public KFDataTransfer(DataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
		initialize();
	}

	private void initialize() {
		try {

			urllist = dataTransfer.getData("text/uri-list");
			text = dataTransfer.getData("text/plain");
			html = dataTransfer.getData("text/html");

			DataTransferExt ext = dataTransfer.<DataTransferExt> cast();

			FileList extFiles = ext.getFiles();
			int len = extFiles.getLength();
			for (int i = 0; i < len; i++) {
				files.add(extFiles.getItem(i));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean hasUrllist() {
		return urllist != null && !urllist.isEmpty();
	}

	public String getUrllist() {
		return urllist;
	}

	public boolean hasText() {
		return text != null && !text.isEmpty();
	}

	public String getText() {
		return text;
	}

	public boolean hasHtml() {
		return html != null && !html.isEmpty();
	}

	public String getHtml() {
		return html;
	}

	public List<File> getFiles() {
		return new ArrayList<File>(files);
	}

	public boolean hasFiles() {
		return files.size() > 0;
	}

	public boolean hasFiles(String mime) {
		if (hasFiles()) {
			for (File f : files) {
				if (f.getType().equals(mime)) {
					return true;
				}
			}
		}
		return false;
	}

}
