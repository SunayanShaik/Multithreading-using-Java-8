package edu.umb.cs.cs681.hw12;

import java.io.File;
import java.nio.file.Paths;

public class AccessCounterDemo {

	public static void main(String[] args) {
		int numOfThreads = 11;
		AccessCounter accessCounter = new AccessCounter();
		String[] folderStructure = {"src", "edu", "umb", "cs", "cs681", "hw12", "file_root"}; 
		String fileFolderPath = Paths.get("").toAbsolutePath().toString() + File.separator + String.join(File.separator,folderStructure);
				
		for(int i=0; i<numOfThreads; i++) {
			RequestHandler requestRunnable = new RequestHandler(accessCounter, fileFolderPath);
			Thread thread = new Thread(requestRunnable);
			thread.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			}
			thread.interrupt();
			requestRunnable.setDone();
		}
	}

}
