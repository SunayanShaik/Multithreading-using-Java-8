package edu.umb.cs.cs681.hw14;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class FileQueue {

	private LinkedList<File> queue;
	private boolean hasQueue = false;
	private ReentrantLock lock;

	public FileQueue() {
		this.queue = new LinkedList<>();
		this.lock = new ReentrantLock();
	}

	public void put(File file) {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " FileQueue-put()]" + " Lock obtained");
		try {
			this.queue.add(file);
			System.out.println("[" + Thread.currentThread().getName() + " FileQueue-put()] added file is: " + file.getName());
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " FileQueue-put()]" + " Lock released");
		}
	}

	public File get() {
		for (File fileElement : queue) {
			lock.lock();
			System.out.println("[" + Thread.currentThread().getName() + " FileQueue-get()]" + " Lock obtained");
			try {
				if (fileElement.getSize() != 0)
					return fileElement;
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " FileQueue-put()]" + " Lock released");
			}

		}
		return null;
	}

	public boolean hasQueue() {
		if (!queue.isEmpty()) {
			hasQueue = true;
		}
		return hasQueue;
	}
	
	public int getSize() {
		return queue.size();
	}
}
