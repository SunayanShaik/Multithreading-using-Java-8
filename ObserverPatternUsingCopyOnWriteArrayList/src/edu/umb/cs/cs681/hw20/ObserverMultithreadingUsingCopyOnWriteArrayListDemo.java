package edu.umb.cs.cs681.hw20;

import java.util.ArrayList;

public class ObserverMultithreadingUsingCopyOnWriteArrayListDemo {

	public static void main(String[] args) {

		Observable observable = new Observable();
		
		ArrayList<Thread> listThreads = new ArrayList<>();
		
		Thread thread1 = new Thread(()->{
			observable.addObserver((o, arg) -> {
				System.out.println("The object added to observer is: " + arg.getClass().getSimpleName() + " : " + arg);
			});
		});
		thread1.start();
		
		Thread thread2 = new Thread(()->{
			observable.addObserver((o, arg) -> {
				System.out.println("The object added to observer is: " + arg.getClass().getSimpleName() + " : " + arg);
			});
		});		
		thread2.start();
		
		Thread thread3 = new Thread(()->{
			observable.addObserver((o, arg) -> {
				System.out.println("The object added to observer is: " + arg.getClass().getSimpleName() + " : " + arg);
			});
		});	
		thread3.start();
		
		listThreads.add(thread1);
		listThreads.add(thread2);
		listThreads.add(thread3);
		
		listThreads.forEach((Thread t)-> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		observable.setChanged();
		observable.notifyObservers("Hello World..!");
		
	}

}
