package edu.umb.cs.cs681.hw14;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class FileCrawler implements Runnable {

	private Directory root; // root dir of a given drive (tree structure)
	private FileQueue queue;
	private boolean done = false;
	private ReentrantLock lock;

	public FileCrawler(FileQueue queue, Directory root) {
		this.queue = queue;
		this.root = root;
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

	private void crawl(Directory root) {
		// Crawl a given drive (tree structure)
		// Put files to a queue. Ignore directories and links.
		ArrayList<FSElement> children = root.getChildren();
		for (FSElement child : children) {
			if (child instanceof File) {
				queue.put((File) child);
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
				}
			}
		}

	}

	public void run() {
		while (true) {
			try {
				lock.lock();
				System.out.println("[" + Thread.currentThread().getName() + " FileCrawler-run()]" + " Lock obtained");
				
				if (done == true) {
					System.out.println("Main thread terminated the [" + Thread.currentThread().getName() + "]");
					break;
				}
				
				if (root != null && queue.getSize() < 30) {
					crawl(root);
				} else {
					Thread.sleep(50);
					System.out.println("No tree structre to crawl..!");
				}
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " FileCrawler-run()]" + " Lock released");
			}
		}
	}
}
