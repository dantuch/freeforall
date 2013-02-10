package de.blogspot.pawelmichalskijavnie.filelisting;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

/**
 * @author dante
 * 
 */
public class FilesListReader implements Runnable {

	private final BlockingQueue<List<File>> queue;
	private final DefaultListModel model;
	private final String searchedFileSuffix;
    private JButton button;

	FilesListReader(BlockingQueue<List<File>> theQueue, DefaultListModel model, JButton runButton) {
	    model.clear();
		this.searchedFileSuffix = ".zip";
		this.model = model;
		this.queue = theQueue;
		this.button = runButton;
	}

	public void run() {
		try {
			while (!FilesListCreator.done) {
				List<File> obj = queue.take();
				process(obj);
			}

			if (FilesListCreator.done) {
				while (queue.size() > 0) {
					List<File> obj = queue.take();
					process(obj);
				}
			}
		} catch (InterruptedException e) {
			throw new AssertionError("Unexpected Interruption");
		} finally {
		    FilesListCreator.done = false;
		    button.setEnabled(true);
		}
	}

	void process(List<File> list) { // FIXME it's just a sample method
		for (File file : list) {
			String path = file.getPath();
			if (path.endsWith(searchedFileSuffix)) {
				model.addElement(path);
			}
		}
	}

}