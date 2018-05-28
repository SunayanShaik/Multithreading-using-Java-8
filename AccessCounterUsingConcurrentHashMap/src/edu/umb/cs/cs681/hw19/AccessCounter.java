package edu.umb.cs.cs681.hw19;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessCounter {
	private ConcurrentHashMap<Path, AtomicInteger> accessCounterConcurrentMap;

	public AccessCounter() {
		accessCounterConcurrentMap = new ConcurrentHashMap<>();
	}

	public void increment(Path pathName) {
		System.out.println("[" + Thread.currentThread().getName() + " increment()] path:" + pathName.toString());
		accessCounterConcurrentMap.putIfAbsent(pathName, new AtomicInteger(0));
		accessCounterConcurrentMap.get(pathName).incrementAndGet();
	}

	public AtomicInteger getCount(Path pathName) {
		return accessCounterConcurrentMap.compute(pathName, (Path path, AtomicInteger countValue) -> {
			return countValue==null?new AtomicInteger(0):countValue;
		});
	}
}
