package edu.umb.cs.cs681.hw11;

public class CancellablePrimesUsing2StepThreadTerminationDemo {
	public static void main(String[] args) {
		CancellablePrimeNumberGenerator cancellablePrimeGen1 = new CancellablePrimeNumberGenerator(1L, 50L);

		Thread thread1 = new Thread(cancellablePrimeGen1);
		
		thread1.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread1.interrupt();
		cancellablePrimeGen1.setDone();
		cancellablePrimeGen1.getPrimes().forEach((prime) -> System.out.println(prime));

	}
}
