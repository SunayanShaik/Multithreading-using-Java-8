package edu.umb.cs.cs681.hw15;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccessCounter {
	private HashMap<Path, Integer> accessCounter;
	private ReentrantReadWriteLock rwLock;
	
	public AccessCounter() {
		accessCounter = new HashMap<>();
		rwLock = new ReentrantReadWriteLock();
	}
	
	public void increment(Path pathName) {
		rwLock.writeLock().lock();
		System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " AccessCounter writeLock obtained");
		try {
			if(accessCounter.containsKey(pathName)) {
				System.out.println("[" + Thread.currentThread().getName() + " increment()] path:" + pathName.toString());
				accessCounter.put(pathName, accessCounter.get(pathName)+1);
			} else {
				accessCounter.put(pathName, 1);
			}
		} finally {
			rwLock.writeLock().unlock();;
			System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " AccessCounter writeLock released");
		}		
	}
	
	public int getCount(Path pathName) {
		rwLock.readLock().lock();
		System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " AccessCounter readLock obtained");
		
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
			rwLock.readLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " AccessCounter readLock released");
		}
		
	}
}
