package edu.umb.cs.cs681.hw11;

import java.util.concurrent.locks.ReentrantLock;

public class CancellablePrimeNumberGenerator extends PrimeNumberGenerator {
	private boolean done = false;
	private ReentrantLock lock;

	public CancellablePrimeNumberGenerator(long from, long to) {
		super(from, to);
		lock = new ReentrantLock();
	}

	public void setDone() {
		lock.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " Lock obtained");
			done = true;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " setDone()]" + " Lock released");
		}		
	}

	public void run() {
		for (long n = from; n <= to; n++) {
			try {
				lock.lock();
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " Lock obtained");
				// Stop generating prime numbers if done==true
				if (done == true) {
					System.out.println("Stopped generating prime numbers.");
					this.primes.clear();
					break;
				}
				if (isPrime(n)) {
					System.out.println(n);
					this.primes.add(n);
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "]" + e.getMessage());
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run()]" + " Lock released");
			}
		}
	}
}