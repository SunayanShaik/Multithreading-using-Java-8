package edu.umb.cs.cs681.hw16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

public class LRUFileCache extends FileCache {

	AccessCounter accessCounter;

	public LRUFileCache(AccessCounter accessCounter) {
		lock = super.lock;
		this.accessCounter = accessCounter;
	}

	@Override
	protected String replace(String targetFile) throws IOException {

		HashMap<String, Date> accessTimeMap = accessCounter.getAccessTimeMap();
		Date mostRecentValue = null;
		String mostRecentKey = null;

		lock.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " LRUFileCache-replace()]" + " Lock obtained");
			for (Entry<String, String> lruEntry : cache.entrySet()) {
				Date d = accessTimeMap.get(lruEntry.getKey());
				if (mostRecentValue == null || d.after(mostRecentValue)) {
					mostRecentValue = d;
					mostRecentKey = lruEntry.getKey().toString();
				}
			}
			cache.remove(mostRecentKey);
			String content = new String(Files.readAllBytes(Paths.get(targetFile)));
			cache.put(targetFile, content);
			return mostRecentKey;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " LRUFileCache-replace()]" + " Lock released");
		}

	}
}
