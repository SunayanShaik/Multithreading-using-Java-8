package edu.umb.cs.cs681.hw17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FIFOFileCacheRW extends FileCache {

	@Override
	protected String replace(String targetFile) throws IOException {

		String key;
		String content;
		
		rwLock.readLock().lock();
		try {
			System.out.println(
					"[" + Thread.currentThread().getName() + " FIFOFileCache-replace()]" + " readLock obtained");
			key = cache.keySet().iterator().next();
			cache.remove(key);
			content = new String(Files.readAllBytes(Paths.get(targetFile)));
			rwLock.writeLock().lock();
		} finally {
			rwLock.readLock().unlock();
			System.out.println(
					"[" + Thread.currentThread().getName() + " FIFOFileCache-replace()]" + " readLock released");
		}
		try {
			System.out.println(
					"[" + Thread.currentThread().getName() + " FIFOFileCache-replace()]" + " writeLock obtained");
			cache.put(targetFile, content);
			System.out.println("The FIFO FileCache replaced the " + "'" + key + "'");
			return key;
		} finally {
			rwLock.writeLock().unlock();
			System.out.println(
					"[" + Thread.currentThread().getName() + " FIFOFileCache-replace()]" + " writeLock released");
		}

	}
}
