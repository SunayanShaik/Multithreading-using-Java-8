package edu.umb.cs.cs681.hw09;

public class SingletonPatternDemoUsingThreads {

	public static void main(String[] args) throws InterruptedException {
		Thread singletonThread1 = new Thread(()->{
			System.out.println("[" + Thread.currentThread().getName() + "] Created Singleton info is : " + Singleton.getInstance());
		});
		
		Thread singletonThread2 = new Thread(()->{
			System.out.println("[" + Thread.currentThread().getName() + "] Created Singleton info is : " + Singleton.getInstance());
		});
		
		Thread singletonThread3 = new Thread(()->{
			System.out.println("[" + Thread.currentThread().getName() + "] Created Singleton info is : " + Singleton.getInstance());
		});
		
		Thread singletonThread4 = new Thread(()->{
			System.out.println("[" + Thread.currentThread().getName() + "] Created Singleton info is : " + Singleton.getInstance());
		});
		
		singletonThread1.start();
		singletonThread1.join();
		singletonThread2.start();
		Thread.sleep(50);
		singletonThread3.start();
		singletonThread4.start();
	}

}
