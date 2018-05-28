package edu.umb.cs.cs681.hw20;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

import edu.umb.cs.cs681.hw20.Observer;

public class Observable {

	protected CopyOnWriteArrayList<Observer<Observable, Object>> observers = new CopyOnWriteArrayList<>();
	private boolean haschanged = false;
	private ReentrantLock lockObs;

	public Observable() {
		this.lockObs = new ReentrantLock();
	}

	public void addObserver(Observer<Observable, Object> observer) {
		if (observer == null)
			throw new NullPointerException();
		observers.add(observer);
	}

	public void deleteObserver(Observer<Observable, Object> observer) {
		int observerIndex = observers.indexOf(observer);
		System.out.println("Observer " + (observerIndex + 1) + " has been deleted");
		observers.remove(observerIndex);
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

	public void notifyObservers(Object arg) {
		lockObs.lock();
		try {
			System.out.println("[" + Thread.currentThread().getName() + " notifyObservers(obj)]" + " lockObs obtained");
			if (!hasChanged())
				return; // balking
			clearChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lockObs.unlock();
			System.out.println("[" + Thread.currentThread().getName() + " notifyObservers(obj)]" + " lockObs released");
		}

		Iterator<Observer<Observable, Object>> it = observers.iterator();
		while (it.hasNext()) {
			it.next().update(this, arg);
		}
	}

	@Override
	public String toString() {
		return String.format("Observable");
	}

}
