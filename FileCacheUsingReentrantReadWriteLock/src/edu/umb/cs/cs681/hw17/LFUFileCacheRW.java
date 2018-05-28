package edu.umb.cs.cs681.hw17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

public class LFUFileCacheRW extends FileCache {

	AccessCounter accessCounter;
	private static int MAX_ACCESS_VALUE = Integer.MAX_VALUE;

	public LFUFileCacheRW(AccessCounter accessCounter) {
		this.accessCounter = accessCounter;
	}

	@Override
	protected String replace(String targetFile) throws IOException {
		String minKey = null;
		Integer minValue = MAX_ACCESS_VALUE;
		String content;
		
		HashMap<String, Integer> countMap = accessCounter.getCountMap();
		rwLock.readLock().lock();
		try {
			System.out.println(
					"[" + Thread.currentThread().getName() + " LFUFileCache-replace()]" + " readLock obtained");
			for (Entry<String, String> entry : cache.entrySet()) {
				Integer value = countMap.get(entry.getKey());
				if (value < MAX_ACCESS_VALUE) {
					minValue = value;
					minKey = entry.getKey().toString();
				}
			}
			content = new String(Files.readAllBytes(Paths.get(targetFile)));
			rwLock.writeLock().lock();
		} finally {
			rwLock.readLock().unlock();
			System.out.println(
					"[" + Thread.currentThread().getName() + " LFUFileCache-replace()]" + " readLock released");
		}
		try {
			System.out.println(
					"[" + Thread.currentThread().getName() + " LFUFileCache-replace()]" + " writeLock obtained");
			cache.remove(minKey);
			cache.put(targetFile, content);
			return minKey;
		} finally {
			rwLock.writeLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " LFUFileCache-replace()]" + " writeLock released");
		}
	}
}
