package edu.umb.cs.cs681.hw16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

public class LFUFileCache extends FileCache {

	AccessCounter accessCounter;
	private static int MAX_ACCESS_VALUE = Integer.MAX_VALUE;

	public LFUFileCache(AccessCounter accessCounter) {
		this.accessCounter = accessCounter;
	}

	@Override
	protected String replace(String targetFile) throws IOException {
		String minKey = null;
		Integer minValue = MAX_ACCESS_VALUE;

		lock.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " LFUFileCache-replace()]" + " Lock obtained");
			HashMap<String, Integer> countMap = accessCounter.getCountMap();

			for (Entry<String, String> entry : cache.entrySet()) {
				Integer value = countMap.get(entry.getKey());
				if (value < MAX_ACCESS_VALUE) {
					minValue = value;
					minKey = entry.getKey().toString();
				}
			}
			cache.remove(minKey);
			String content = new String(Files.readAllBytes(Paths.get(targetFile)));
			cache.put(targetFile, content);
			return minKey;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " LFUFileCache-replace()]" + " Lock released");
		}
	}
}
