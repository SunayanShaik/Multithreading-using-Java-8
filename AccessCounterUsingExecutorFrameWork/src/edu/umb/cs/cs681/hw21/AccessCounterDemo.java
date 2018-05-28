package edu.umb.cs.cs681.hw21;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AccessCounterDemo {

	public static void main(String[] args) throws InterruptedException {
		int numOfThreads = 5;
		ArrayList<RequestHandler> listRequestRunnables = new ArrayList<>();
		RequestHandler requestRunnable;
		AccessCounter accessCounter = new AccessCounter();
		String[] folderStructure = { "src", "edu", "umb", "cs", "cs681", "hw21", "file_root" };
		String fileFolderPath = Paths.get("").toAbsolutePath().toString() + File.separator
				+ String.join(File.separator, folderStructure);

		for (int i = 0; i < numOfThreads; i++) {
			requestRunnable = new RequestHandler(accessCounter, fileFolderPath);
			listRequestRunnables.add(requestRunnable);
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);

		listRequestRunnables.forEach((requestRunnableObj) -> {
			executor.execute(requestRunnableObj);
		});
				
		executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
		
		listRequestRunnables.forEach((requestRunnableObj) -> {
			requestRunnableObj.setDone();
		});
		
		executor.shutdown();

	}
}
