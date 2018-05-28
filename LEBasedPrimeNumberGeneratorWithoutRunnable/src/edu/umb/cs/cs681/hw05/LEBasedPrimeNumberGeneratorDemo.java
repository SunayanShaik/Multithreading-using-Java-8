package edu.umb.cs.cs681.hw05;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LEBasedPrimeNumberGeneratorDemo {

	public static void main(String[] args) {
		PrimeNumberGenerator primeNumberGenerator = new PrimeNumberGenerator();

		Thread thread = new Thread(() -> {
			primeNumberGenerator.primes = LongStream.rangeClosed(1L, 100L)
					.filter(n -> primeNumberGenerator.isPrime(n)).boxed()
					.collect(Collectors.toList());
		});
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("The prime numbers generated (without runnable) in the range [1L, 100L] are : ");
		primeNumberGenerator.getPrimes().forEach(primes -> System.out.println(primes));
	}

}
