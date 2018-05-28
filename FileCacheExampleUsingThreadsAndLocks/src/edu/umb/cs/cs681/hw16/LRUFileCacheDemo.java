package edu.umb.cs.cs681.hw16;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LRUFileCacheDemo {

	public static void main(String[] args) {
		int numOfThreads = 1;
		AccessCounter accessCounter = new AccessCounter();
		String[] folderStructure = { "src", "edu", "umb", "cs", "cs681", "hw16", "file_root" };
		String fileFolderPath = Paths.get("").toAbsolutePath().toString() + File.separator + String.join(File.separator, folderStructure);

		System.out.println("******************************** LRU FileCache replace() starts ********************************");
		FileCache lruFileCache = new LRUFileCache(accessCounter);
		ArrayList<Thread> lruListThreads = new ArrayList<>();
		ArrayList<RequestHandler> lruListRequestRunnables = new ArrayList<>();
		
		for (int i = 0; i < numOfThreads; i++) {
			RequestHandler requestRunnable = new RequestHandler(accessCounter, fileFolderPath, lruFileCache);
			lruListRequestRunnables.add(requestRunnable);
			Thread thread = new Thread(requestRunnable);
			lruListThreads.add(thread);
			thread.start();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			}
		}
		
		for(Thread thread : lruListThreads) {
			thread.interrupt();
		}
		
		for(RequestHandler  requestRunnable: lruListRequestRunnables) {
			requestRunnable.setDone();
		}
	}

}
