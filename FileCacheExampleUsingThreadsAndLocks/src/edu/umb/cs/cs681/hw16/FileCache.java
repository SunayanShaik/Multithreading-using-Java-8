package edu.umb.cs.cs681.hw16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FileCache {
	protected LinkedHashMap<String, String> cache;
	public static final int SIZE_OF_CACHE = 5;
	protected ReentrantLock lock;
	
	public FileCache() {
		cache = new LinkedHashMap<>(SIZE_OF_CACHE);
		lock = new ReentrantLock();
	}

	public String fetch(String targetFile) throws IOException {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " fetch()]" + " Lock obtained");
		try {
			if(cache.containsKey(targetFile)) {
				return cache.get(targetFile);
			} 
			
			if(cache.size() < SIZE_OF_CACHE) {
				return cacheFile(targetFile);
			} else {
				return replace(targetFile);
			}
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " fetch()]" + " Lock released");
		}
		
	}

	private String cacheFile(String targetFile) throws IOException {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " cacheFile()]" + " Lock obtained");
		try {
			String content = new String(Files.readAllBytes(Paths.get(targetFile)));
			System.out.println("File has been cached.");
			return cache.put(targetFile, content);
		} catch(Exception e){
			throw e;
		}finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " cacheFile()]" + " Lock released");
		}		
	}

	protected abstract String replace(String targetFile) throws IOException;
}
