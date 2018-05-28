package edu.umb.cs.cs681.hw07;

import java.util.concurrent.locks.ReentrantLock;

import edu.umb.cs.cs681.hw07.PrimeNumberGenerator;

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
			System.out.println("[" + Thread.currentThread().getName() + " run]" +" Lock obtained for setting true = done.");
			done = true;
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " run]" +" Lock released");
		}		
	}

	public void run() {
		for (long n = from; n <= to; n++) {
			try {
				lock.lock();
				System.out.println("[" + Thread.currentThread().getName() + " run]" + " Lock obtained");
				// Stop generating prime numbers if done==true
				if (done == true) {
					System.out.println("[" + Thread.currentThread().getName() + " run]" +" Stopped generating prime numbers.");
					this.primes.clear();
					break;
				}
				if (isPrime(n)) {
					this.primes.add(n);
				}
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " run]" + " Lock released");
			}
		}
	}
}