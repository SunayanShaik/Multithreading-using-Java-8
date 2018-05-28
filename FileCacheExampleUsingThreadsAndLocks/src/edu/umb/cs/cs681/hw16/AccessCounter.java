package edu.umb.cs.cs681.hw16;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
	private HashMap<String, Integer> accessCounter;
	public static int MAX_ACCESS_VALUE = Integer.MAX_VALUE;
	private HashMap<String, Date> accessTimeMap;
	private ReentrantLock lock;

	public AccessCounter() {
		accessCounter = new HashMap<>();
		this.accessTimeMap = new HashMap<>();
		lock = new ReentrantLock();
	}

	public void increment(String randomFilePath) {
		lock.lock();
		
		try {
			System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " Lock obtained");
			if (accessCounter.containsKey(randomFilePath)) {
				System.out.println(
						"[" + Thread.currentThread().getName() + " increment()] path:" + randomFilePath.toString());
				accessCounter.put(randomFilePath, accessCounter.get(randomFilePath) + 1);
			} else {
				accessCounter.put(randomFilePath, 1);
			}
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " Lock released");
		}
	}

	public int getCount(String pathName) {
		lock.lock();

		try {
			System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " Lock obtained");
			int count = 0;
			if (accessCounter.containsKey(pathName)) {
				count = accessCounter.get(pathName);
				System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " Path:"
						+ pathName.toString() + " Count:" + count);
				return count;
			} else {
				return 0;
			}
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " getCount()]" + " Lock released");
		}

	}

	public String getLRUKey() {
		lock.lock();
		Date mostRecentValue = null;
		String mostRecentKey = null;
		try {
			System.out.println("[" + Thread.currentThread().getName() + " getLRUKey()]" + " Lock obtained");
			for (Entry<String, Date> lruEntry : accessTimeMap.entrySet()) {
				Date d = lruEntry.getValue();
				if (mostRecentValue == null || d.after(mostRecentValue)) {
					mostRecentValue = d;
					mostRecentKey = lruEntry.getKey().toString();
				}
			}
			return mostRecentKey;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " getLRUKey()]" + " Lock released");
		}
	}

	public void insertIntoAccessTimeMap(String targetFile) {
		accessTimeMap.put(targetFile, new Date());
	}

	public HashMap<String, Integer> getCountMap() {
		return accessCounter;
	}

	public HashMap<String, Date> getAccessTimeMap() {
		return accessTimeMap;
	}
}
