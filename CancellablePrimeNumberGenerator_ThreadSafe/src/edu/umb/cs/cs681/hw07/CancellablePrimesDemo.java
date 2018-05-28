package edu.umb.cs.cs681.hw07;

public class CancellablePrimesDemo {
	public static void main(String[] args) {

		final int numOfThreads = 3;

		CancellablePrimeNumberGenerator cancellablePrimeGenerator1 = new CancellablePrimeNumberGenerator(10L, 100L);

		for (int i = 0; i < numOfThreads; i++) {
			new Thread(cancellablePrimeGenerator1).start();
		}

		cancellablePrimeGenerator1.setDone();

		cancellablePrimeGenerator1.getPrimes().forEach((prime) -> System.out.println(prime));
	}
}
