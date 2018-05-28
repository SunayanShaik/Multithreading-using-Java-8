package edu.umb.cs.cs681.hw10.part2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SecurityGate {
	AtomicInteger counter = new AtomicInteger(0);
	private static SecurityGate instance = null;
	private ReentrantLock lock = new ReentrantLock(); // lock to guard counter
														// variable.
	private static ReentrantLock sLock = new ReentrantLock(); // lock to guard
																// instance
																// variable.

	public static SecurityGate getInstance() {
		try {
			sLock.lock();
			System.out.println(
					"[" + Thread.currentThread().getName() + " SecurityGate - getInstance()]" + " sLock obtained");
			if (instance == null) {
				instance = new SecurityGate();
				System.out.println("[" + Thread.currentThread().getName() + " SecurityGate - getInstance()]"
						+ " Created a new instance.");
			}
		} finally {
			sLock.unlock();
			System.out.println(
					"[" + Thread.currentThread().getName() + " SecurityGate - getInstance()]" + " sLock released");
		}		
		return instance;
	}

	public void enter() {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " SecurityGate - enter()]" + " lock obtained");

		try {
			counter.updateAndGet(count->count+1);
			System.out.println(
					"[" + Thread.currentThread().getName() + " SecurityGate - enter()]" + " counter incremented value is :" + counter);
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " SecurityGate - enter()]" + " lock released");
		}
	}

	public void exit() {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " SecurityGate - exit()]" + " lock obtained");
		try {
			counter.updateAndGet(count->count-1);
			System.out.println(
					"[" + Thread.currentThread().getName() + " SecurityGate - enter()]" + " counter decremented value is :" + counter);
		} finally {
			lock.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " SecurityGate - exit()]" + " lock released");

		}
	}

	// Get the # of guests in the gate
	public AtomicInteger getCount() {
		System.out.println(
				"[" + Thread.currentThread().getName() + " SecurityGate - getCount()]" + " Count is : " + counter);
		return this.counter;
	}
}
