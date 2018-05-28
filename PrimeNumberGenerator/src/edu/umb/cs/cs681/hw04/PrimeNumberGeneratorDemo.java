package edu.umb.cs.cs681.hw04;

public class PrimeNumberGeneratorDemo {

	public static void main(String[] args) {
		StreamBasedPrimeNumberGenerator gen1 = new StreamBasedPrimeNumberGenerator(1L, 1000000L);
		StreamBasedPrimeNumberGenerator gen2 = new StreamBasedPrimeNumberGenerator(1000000L, 2000000L);
		Thread t1 = new Thread(gen1);
		Thread t2 = new Thread(gen2);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
			System.out.println("The prime numbers generated in the range of (1L, 1000000L) are : ");
			gen1.getPrimes().forEach(primes->System.out.println(primes));
			
			System.out.println("The prime numbers generated in the range of (1000000L, 2000000L) are : ");
			gen2.getPrimes().forEach(primes->System.out.println(primes));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
		
	}
}
