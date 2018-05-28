package edu.umb.cs.cs681.hw14;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FileIndexer implements Runnable {

	private FileQueue queue;
	private boolean done = false;
	private LinkedHashMap<Integer, File> indexOfFile;
	private ReentrantLock lock;

	public FileIndexer(FileQueue queue) {
		this.queue = queue;
		this.indexOfFile = new LinkedHashMap<>();
		this.lock = new ReentrantLock();
	}

	public void setDone() {
		this.lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " Lock obtained");
		try {
			this.done = true;
		} finally {
			this.lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " Lock released");
		}
	}

	public void run() {
		while (true) {
			try {
				lock.lock();
				System.out.println("[" + Thread.currentThread().getName() + " FileIndexer-run()]" + " Lock obtained");

				if (!queue.hasQueue()) {
					Thread.sleep(50);
					break;
				} else {
					indexFile(queue.get());
				}

				if (done == true) {
					System.out.println("[" + Thread.currentThread().getName()
							+ " FileIndexer-run()] is terminated by main thread...");
					break;
				}

			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " FileIndexer-run()]" + " Lock released");
			}
		}
	}

	public void indexFile(File file) {
		try {
			lock.lock();
			System.out.println("[" + Thread.currentThread().getName() + " FileIndexer-indexFile()]" + " Lock obtained");
			indexOfFile.put(file.getSize(), file);
			System.out.println("Indexed file is : " + file.getName());
			Thread.sleep(0);
		} catch (InterruptedException e) {
			System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " FileIndexer-indexFile()]" + " Lock released");
		}
	}
}
