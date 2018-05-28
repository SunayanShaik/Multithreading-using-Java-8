package edu.umb.cs.cs681.hw17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class FileCache {
	protected LinkedHashMap<String, String> cache;
	public static final int SIZE_OF_CACHE = 5;
	protected ReentrantReadWriteLock rwLock;

	public FileCache() {
		cache = new LinkedHashMap<>(SIZE_OF_CACHE);
		rwLock = new ReentrantReadWriteLock();
	}

	public String fetch(String targetFile) throws IOException {
		rwLock.writeLock().lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " fetch()]" + " writeLock obtained");
			if (!cache.containsKey(targetFile)) {
				if (cache.size() < SIZE_OF_CACHE)
					return cacheFile(targetFile);
				else
					return replace(targetFile);
			}
			rwLock.readLock().lock(); // downgrading

		} finally {
			rwLock.writeLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " fetch()]" + " writeLock released");
		}
		try {
			System.out.println("[" + Thread.currentThread().getName() + " fetch()]" + " readLock obtained");
			return cache.get(targetFile);
		} finally {
			rwLock.readLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " fetch()]" + " readLock released");
		}

	}

	private String cacheFile(String targetFile) throws IOException {
		rwLock.writeLock().lock();
		System.out.println("[" + Thread.currentThread().getName() + " cacheFile()]" + " writeLock obtained");
		try {
			String content = new String(Files.readAllBytes(Paths.get(targetFile)));
			System.out.println("File has been cached.");
			return cache.put(targetFile, content);
		} catch (Exception e) {
			throw e;
		} finally {
			rwLock.writeLock().unlock();
			System.out.println("[" + Thread.currentThread().getName() + " cacheFile()]" + " writeLock released");
		}
	}

	protected abstract String replace(String targetFile) throws IOException;
}
