package edu.umb.cs.cs681.hw01;

public class ConcreteObserverPatternLE {

	public static void main(String[] args) {

		Observable observable1 = new Observable();
		
		System.out.println("The elements of the observer are : ");
		
		//Use lambda expression to add an observer
		observable1.addObserver((o, obj) -> {
			System.out.println(obj);
		});		
		
		observable1.setChanged();
		observable1.notifyObservers("Hello World..!");
		observable1.notifyObservers(2);
		observable1.notifyObservers(205.50);
		
	}

}