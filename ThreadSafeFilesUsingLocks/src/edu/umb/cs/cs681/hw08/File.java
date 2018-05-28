package edu.umb.cs.cs681.hw08;

import java.sql.Timestamp;
import java.util.concurrent.locks.ReentrantLock;

public class File {
	private boolean changed = false;
	private ReentrantLock lock = new ReentrantLock();
	
	public ReentrantLock getLock() {
		return this.lock;
	}
	
	public void change() {
		System.out.println("[" + Thread.currentThread().getName() + "]" + " File change() status set to true " 
					+ new Timestamp(System.currentTimeMillis()));
		changed = true;
	}

	public void save() {
		if (changed == false) {
			System.out.println("[" + Thread.currentThread().getName() + "]" + " No File Changes "
					+ new Timestamp(System.currentTimeMillis()));
			return;
		}

		if (changed == true) {
			System.out.println("[" + Thread.currentThread().getName() + "]" + " File changes saved "
					+ new Timestamp(System.currentTimeMillis()));
				changed = false;	
		}
	}
}
