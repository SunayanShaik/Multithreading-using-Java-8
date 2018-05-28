package edu.umb.cs.cs681.hw08;

import java.util.concurrent.locks.ReentrantLock;

public class AutoSaver implements Runnable {
	private boolean done = false;
	private ReentrantLock lock;
	private File aFile;
	
	public AutoSaver(File aFile) {
		this.aFile = aFile;
		this.lock = aFile.getLock();
	}

	@Override
	public void run() {
		while (true) {
			lock.lock();
			System.out.println("[" + Thread.currentThread().getName() + " run]" + " Lock obtained");
			try {
				if (done == true) {
					System.out.println("[" + Thread.currentThread().getName() + "]" + " Editor changes done");
					break;
				}

				aFile.save();

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run]" + " Lock released");
			}
		}
	}

	public void setDone() {
		lock.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " autoSaver-setDone]" + " Lock obtained");
			done = true;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " autoSaver-setDone]" + " Lock released");
		}
	}

}
