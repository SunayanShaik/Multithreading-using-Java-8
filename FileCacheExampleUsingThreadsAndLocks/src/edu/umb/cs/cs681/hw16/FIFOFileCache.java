package edu.umb.cs.cs681.hw16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FIFOFileCache extends FileCache {

	@Override
	protected String replace(String targetFile) throws IOException {
		lock.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " FIFOFileCache-replace()]" + " Lock obtained");
			String key = cache.keySet().iterator().next();
			cache.remove(key);
			String content = new String(Files.readAllBytes(Paths.get(targetFile)));
			cache.put(targetFile, content);
			System.out.println("The FIFO FileCache replaced the " + "'" + key + "'");
			return key;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " FIFOFileCache-replace()]" + " Lock released");
		}

	}
}
