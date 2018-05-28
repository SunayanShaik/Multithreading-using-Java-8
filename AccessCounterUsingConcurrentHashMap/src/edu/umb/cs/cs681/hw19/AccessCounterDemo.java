package edu.umb.cs.cs681.hw19;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AccessCounterDemo {

	public static void main(String[] args) {
		int numOfThreads = 11;
		AccessCounter accessCounter = new AccessCounter();
		String[] folderStructure = {"src", "edu", "umb", "cs", "cs681", "hw19", "file_root"}; 
		String fileFolderPath = Paths.get("").toAbsolutePath().toString() + File.separator + String.join(File.separator,folderStructure);

		ArrayList<Thread> accessCounterThreads = new ArrayList<>();
		ArrayList<RequestHandler> accessCounterRunnables = new ArrayList<>();

		for(int i=0; i<numOfThreads; i++) {
			RequestHandler requestRunnable = new RequestHandler(accessCounter, fileFolderPath);
			accessCounterRunnables.add(requestRunnable);
			Thread thread = new Thread(requestRunnable);
			accessCounterThreads.add(thread);
			thread.start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			}
		}
				
		accessCounterThreads.forEach((Thread t) -> {
				t.interrupt();
		});
		
		accessCounterRunnables.forEach((RequestHandler requestHandler) -> {
			requestHandler.setDone();
		});
	}

}
