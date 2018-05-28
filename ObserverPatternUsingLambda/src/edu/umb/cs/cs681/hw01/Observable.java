package edu.umb.cs.cs681.hw01;

import java.util.ArrayList;

public class Observable {

	protected ArrayList<Observer<Observable, Object>> observers = new ArrayList<>();
	private boolean haschanged = false;

	public Observable() {

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
		haschanged = true;
	}

	public boolean hasChanged() {
		return haschanged;
	}

	protected void clearChanged() {
		haschanged = false;
	}

	public void notifyObservers(Object obj) {
		observers.forEach(observer -> observer.update(this, obj));
		clearChanged();
	}

	public void notifyObservers() {
		notifyObservers(null);
	}

	@Override
	public String toString() {
		return String.format("Observable");
	}
	
}