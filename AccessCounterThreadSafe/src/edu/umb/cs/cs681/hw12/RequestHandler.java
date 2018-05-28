package edu.umb.cs.cs681.hw12;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHandler implements Runnable {

	private String fileFolderpath;
	private AccessCounter accesCounter;
	private boolean done = false;
	private ReentrantLock lock;

	public RequestHandler(AccessCounter accesCounter, String pathName) {
		this.fileFolderpath = pathName;
		this.accesCounter = accesCounter;
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
			Path randomFilePath = randomFile.toPath();
			accesCounter.increment(randomFilePath);
			accesCounter.getCount(randomFilePath);
			lock.lock();
			try {
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " Lock obtained");
				Thread.sleep(1000);

				if (done == true) {
					break;
				}

			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " Lock released");
			}
		}
	}
}
