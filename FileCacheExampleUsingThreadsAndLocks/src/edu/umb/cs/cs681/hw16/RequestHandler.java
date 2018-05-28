package edu.umb.cs.cs681.hw16;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHandler implements Runnable {

	private String fileFolderpath;
	private AccessCounter accesCounter;
	private FileCache fileCache;
	private boolean done = false;
	private ReentrantLock lock;

	public RequestHandler(AccessCounter accesCounter, String pathName, FileCache fileCache) {
		this.fileFolderpath = pathName;
		this.accesCounter = accesCounter;
		this.fileCache = fileCache;
		lock = new ReentrantLock();
	}

	public void setDone() {
		lock.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " Lock obtained");
			done = true;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " Lock released");
		}
	}

	public void run() {
		while (true) {
			File folder = new File(this.fileFolderpath);
			File[] htmlFileArray = folder.listFiles();
			int randomNumber = (int) Math.floor(Math.random() * htmlFileArray.length);
			File randomFile = htmlFileArray[randomNumber];
			String randomFilePath = randomFile.toPath().toString();
			
			lock.lock();
			try {
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " Lock obtained");
				//Chooses a file at random and calls fetch() for that file.
				fileCache.fetch(randomFilePath.toString());
				accesCounter.insertIntoAccessTimeMap(randomFilePath.toString());
				accesCounter.increment(randomFilePath);
				accesCounter.getCount(randomFilePath);
				
				Thread.sleep(50);

				if (done == true) {
					break;
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " Lock released");
			}
		}
	}
}
