package edu.umb.cs.cs681.hw08;

import java.util.concurrent.locks.ReentrantLock;

public class Editor implements Runnable {
	private boolean done = false;
	private ReentrantLock lockEd;
	private File aFile;
	
	public Editor(File aFile) {
		this.aFile = aFile;
		this.lockEd = aFile.getLock();
	}

	@Override
	public void run() {
		while (true) {
			lockEd.lock();
			System.out.println("[" + Thread.currentThread().getName() + " run]" + " Lock obtained");
			try {
				if (done == true) {
					System.out.println("[" + Thread.currentThread().getName() + "]" + " Editor changes done");
					break;
				}

				aFile.change();
				aFile.save();

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lockEd.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run]" + " Lock released");
			}
		}
	}

	public void setDone() {
		lockEd.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " editor-setDone]" + " Lock obtained");
			done = true;
		} finally {
			lockEd.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " editor-setDone]" + " Lock released");
		}	
	}
}
