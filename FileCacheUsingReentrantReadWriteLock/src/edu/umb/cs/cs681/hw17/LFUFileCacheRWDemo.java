package edu.umb.cs.cs681.hw17;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LFUFileCacheRWDemo {

	public static void main(String[] args) {
		
		int numOfThreads = 1;
		AccessCounter accessCounter = new AccessCounter();
		String[] folderStructure = { "src", "edu", "umb", "cs", "cs681", "hw17", "file_root" };
		String fileFolderPath = Paths.get("").toAbsolutePath().toString() + File.separator + String.join(File.separator, folderStructure);

		System.out.println("******************************** LFU FileCache replace() starts ********************************");
		FileCache lfuFileCache = new LFUFileCacheRW(accessCounter);
		ArrayList<Thread> lfuListThreads = new ArrayList<>();
		ArrayList<RequestHandler> lfuListRequestRunnables = new ArrayList<>();
		
		for (int i = 0; i < numOfThreads; i++) {
			RequestHandler requestRunnable = new RequestHandler(accessCounter, fileFolderPath, lfuFileCache);
			lfuListRequestRunnables.add(requestRunnable);
			Thread thread = new Thread(requestRunnable);
			lfuListThreads.add(thread);
			thread.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			}
		}
		
		for(Thread thread : lfuListThreads) {
			thread.interrupt();
		}
		
		for(RequestHandler  requestRunnable: lfuListRequestRunnables) {
			requestRunnable.setDone();
		}

	}

}
