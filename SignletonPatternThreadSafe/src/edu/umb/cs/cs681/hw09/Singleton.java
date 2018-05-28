package edu.umb.cs.cs681.hw09;

import java.util.concurrent.locks.ReentrantLock;

public class Singleton {

	private static Singleton instance = null;
	private static ReentrantLock lock = new ReentrantLock();

	private Singleton() {

	};

	public static Singleton getInstance() {
		try {
			lock.lock();
			System.out.println("[" + Thread.currentThread().getName() + " getInstance()]" + " Lock obtained");
			if (instance == null) {
				instance = new Singleton();
				System.out.println("[" + Thread.currentThread().getName() + " getInstance()]" + " Created a new instance");
			}
			return instance;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " getInstance()]" + " Lock released");
		}
	}

}
