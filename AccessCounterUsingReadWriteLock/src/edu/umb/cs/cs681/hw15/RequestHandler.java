package edu.umb.cs.cs681.hw15;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RequestHandler implements Runnable {

	private String fileFolderpath;
	private AccessCounter accesCounter;
	private boolean done = false;
	private ReentrantReadWriteLock rwLock;

	public RequestHandler(AccessCounter accesCounter, String pathName) {
		this.fileFolderpath = pathName;
		this.accesCounter = accesCounter;
		rwLock = new ReentrantReadWriteLock();
	}

	public void setDone() {
		rwLock.writeLock().lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " done writeLock obtained");
			done = true;
		} finally {
			rwLock.writeLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " done writeLock released");
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
			rwLock.readLock().lock();
			try {
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " done readLock obtained");
				Thread.sleep(1000);

				if (done == true) {
					break;
				}

			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			} finally {
				rwLock.readLock().unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " done readLock released");
			}
		}
	}
}
