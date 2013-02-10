package de.blogspot.pawelmichalskijavnie.filelisting;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author dante
 * 
 */
public class FilesListCreator implements Runnable {

	private static final int filesBuffer = 100; //max size of tempFilesList
	private static final int recusionLevelOfRoot = -1;
	private final BlockingQueue<List<File>> queue;
	private final File dirToList;

	private List<File> tempFilesList = new LinkedList<File>();
	private int recursionLevel;
	private int filesFound;
	private boolean foundLast;
	public volatile static boolean done;

	FilesListCreator(BlockingQueue<List<File>> theQueue, File directoryToList) {
		this.queue = theQueue;
		this.dirToList = directoryToList;
	}

	public void run() {
		try {
			while (!foundLast) {
				listFiles(dirToList);
			}
			finalizeListing();
		} catch (InterruptedException e) {
			throw new AssertionError("Unexpected Interruption");
		}
	}

	public BlockingQueue<List<File>> listFiles(File root) throws InterruptedException {
		if (root != null) {
			if (root.isDirectory()) {
				processChildren(root);
			}
			addRootDirecotryToQueue(root);
		}
		return queue;
	}

	private void processChildren(File root) throws InterruptedException {
		for (File child : root.listFiles()) {
			if (child.isDirectory()) {
				recursionLevel++;
				processChildren(child);
			}
			tempFilesList.add(child);
			filesFound++;
			if (filesFound % filesBuffer == 0) {
				queue.put(tempFilesList);
				tempFilesList = new LinkedList<File>();
			}
		}
		recursionLevel--;
	}

	private void addRootDirecotryToQueue(File root) throws InterruptedException {
		if (recursionLevel == recusionLevelOfRoot) {
			foundLast = true;
			tempFilesList.add(root);
			queue.put(tempFilesList);
		}
	}

	private void finalizeListing() throws InterruptedException {
		done = true;

		// Add one last element to Blocking Queue to prevent 'take' method
		// in Consumer class from lasting for ever
		queue.put(new LinkedList<File>());
	}
}