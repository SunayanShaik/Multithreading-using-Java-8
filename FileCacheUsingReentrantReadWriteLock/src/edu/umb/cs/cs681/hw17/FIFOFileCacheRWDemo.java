package edu.umb.cs.cs681.hw17;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FIFOFileCacheRWDemo {

	public static void main(String[] args) {
		
		int numOfThreads = 1;
		AccessCounter accessCounter = new AccessCounter();
		String[] folderStructure = { "src", "edu", "umb", "cs", "cs681", "hw17", "file_root" };
		String fileFolderPath = Paths.get("").toAbsolutePath().toString() + File.separator + String.join(File.separator, folderStructure);

		
		System.out.println("******************************** FIFO FileCache replace() starts *******************************");
		FileCache fifoFileCache = new FIFOFileCacheRW();
		ArrayList<Thread> fifoListThreads = new ArrayList<>();
		ArrayList<RequestHandler> fifoListRequestRunnables = new ArrayList<>();
		
		for (int i = 0; i < numOfThreads; i++) {
			RequestHandler requestRunnable = new RequestHandler(accessCounter, fileFolderPath, fifoFileCache);
			fifoListRequestRunnables.add(requestRunnable);
			Thread thread = new Thread(requestRunnable);
			fifoListThreads.add(thread);
			thread.start();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			}
		}
		
		for(Thread thread : fifoListThreads) {
			thread.interrupt();
		}
		
		for(RequestHandler  requestRunnable: fifoListRequestRunnables) {
			requestRunnable.setDone();
		}
	}

}
