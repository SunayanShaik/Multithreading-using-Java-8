package edu.umb.cs.cs681.hw17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

public class LRUFileCacheRW extends FileCache {

	AccessCounter accessCounter;

	public LRUFileCacheRW(AccessCounter accessCounter) {
		rwLock = super.rwLock;
		this.accessCounter = accessCounter;
	}

	@Override
	protected String replace(String targetFile) throws IOException {

		HashMap<String, Date> accessTimeMap = accessCounter.getAccessTimeMap();
		Date mostRecentValue = null;
		String mostRecentKey = null;
		String content;

		rwLock.readLock().lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " LRUFileCache-replace()]" + " readLock obtained");
			
			for (Entry<String, String> lruEntry : cache.entrySet()) {
				Date d = accessTimeMap.get(lruEntry.getKey());
				if (mostRecentValue == null || d.after(mostRecentValue)) {
					mostRecentValue = d;
					mostRecentKey = lruEntry.getKey().toString();
				}
			}
			content = new String(Files.readAllBytes(Paths.get(targetFile)));
			rwLock.writeLock().lock();
			} finally {
				rwLock.readLock().unlock();
				System.out.println("[" + Thread.currentThread().getName() + " LRUFileCache-replace()]" + " readLock released");
			}
		try {
			System.out.println("[" + Thread.currentThread().getName() + " LRUFileCache-replace()]" + " writeLock obtained");	
			cache.remove(mostRecentKey);
			cache.put(targetFile, content);
			return mostRecentKey;
		} finally {
			rwLock.writeLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " LRUFileCache-replace()]" + " writeLock released");
		}

	}
}
