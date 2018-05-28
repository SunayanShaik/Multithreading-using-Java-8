package edu.umb.cs.cs681.hw21;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
	private HashMap<Path, Integer> accessCounter;
	private ReentrantLock lock;
	
	public AccessCounter() {
		accessCounter = new HashMap<>();
		lock = new ReentrantLock();
	}
	
	public void increment(Path pathName) {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " Lock obtained");
		try {
			if(accessCounter.containsKey(pathName)) {
				System.out.println("[" + Thread.currentThread().getName() + " increment()] path:" + pathName.toString());
				accessCounter.put(pathName, accessCounter.get(pathName)+1);
			} else {
				accessCounter.put(pathName, 1);
				System.out.println("[" + Thread.currentThread().getName() + " increment()] path:" + pathName.toString());
			}
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " Lock released");
		}		
	}
	
	public int getCount(Path pathName) {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " Lock obtained");
		
		try {
			int count = 0;
			if(accessCounter.containsKey(pathName)) {
				count = accessCounter.get(pathName);
				System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " Path:" + pathName.toString() + " Count:" + count);
				return count;
			} else {
				return 0;
			}
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " Lock released");
		}
		
	}
}
