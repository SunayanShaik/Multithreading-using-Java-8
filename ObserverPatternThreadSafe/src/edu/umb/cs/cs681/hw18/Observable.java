package edu.umb.cs.cs681.hw18;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class Observable {

	protected Vector<Observer<Observable, Object>> observers = new Vector<>();
	private boolean haschanged = false;
	private ReentrantLock lockObs;

	public Observable() {
		this.lockObs = new ReentrantLock();
	}

	public void addObserver(Observer<Observable, Object> observer) {
		lockObs.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " addObserver()]" + " lockObs obtained");
			if (observer == null)
				throw new NullPointerException();
			observers.add(observer);
		} finally {
			lockObs.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " increment()]" + " lockObs released");
		}
	}

	public void deleteObserver(Observer<Observable, Object> observer) {
		lockObs.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " deleteObserver()]" + " lockObs obtained");
			int observerIndex = observers.indexOf(observer);
			System.out.println("Observer " + (observerIndex + 1) + " has been deleted");
			observers.remove(observerIndex);
		} finally {
			lockObs.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " deleteObserver()]" + " lockObs released");
		}
	}

	protected void setChanged() {
		lockObs.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " setChanged()]" + " lockObs obtained");
			haschanged = true;
			System.out.println("[" + Thread.currentThread().getName() + "]" + " has set hasChanged = " + haschanged);
		} finally {
			lockObs.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " setChanged()]" + " lockObs released");
		}
	}

	public boolean hasChanged() {
		return haschanged;
	}

	protected void clearChanged() {
		lockObs.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " clearChanged()]" + " lockObs obtained");
			haschanged = false;
			System.out.println("[" + Thread.currentThread().getName() + "]" + " has set hasChanged = " + haschanged);
		} finally {
			lockObs.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " clearChanged()]" + " lockObs released");
		}
	}

	@SuppressWarnings("unchecked")
	public void notifyObservers(Object arg) {
		Object[] arrLocal = new Object[observers.size()];

		lockObs.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " notifyObservers(obj)]" + " lockObs obtained");
			if (!hasChanged())
				return; // balking
			arrLocal = observers.toArray(); // observers copied to arrLocal
			clearChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lockObs.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " notifyObservers(obj)]" + " lockObs released");
		}

		for (int i = arrLocal.length - 1; i >= 0; i--) {
			((Observer<Observable, Object>) arrLocal[i]).update(this, arg); // open
																			// call
			System.out
					.println("[" + Thread.currentThread().getName() + "]" + " notify observers with the '" + arg + "'");
		}

	}

	@Override
	public String toString() {
		return String.format("Observable");
	}

}
