package de.blogspot.pawelmichalskijavnie.filelisting;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

/**
 * @author dante
 * 
 */
public class FilesListerAsProducerConsumer implements Runnable {

	private final DefaultListModel model;
	private final String pathname;
    private JButton runButton;

	FilesListerAsProducerConsumer(DefaultListModel model, String pathname, JButton runButton) {
		this.model = model;
		this.pathname = pathname;
		this.runButton = runButton;
		runButton.setEnabled(false);
	}

	public void run() {
		BlockingQueue<List<File>> myQueue = new LinkedBlockingQueue<List<File>>();
		
		File directoryToList = new File(pathname);
		new Thread(new FilesListCreator(myQueue, directoryToList)).start();

		new Thread(new FilesListReader(myQueue, model, runButton)).start();

	}

}
