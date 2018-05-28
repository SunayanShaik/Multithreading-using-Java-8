package edu.umb.cs.cs681.hw05;

import java.util.List;

public class PrimeNumberGenerator {
	protected List<Long> primes;

	public List<Long> getPrimes() {
		return primes;
	};

	protected boolean isEven(long n) {
		if (n % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean isPrime(long n) {
		// 1 is not prime.
		if (n == 1) {
			return false;
		}
		// Even numbers that are greater than 2 are not prime.
		if (n > 2 && isEven(n)) {
			return false;
		}
		long i;
		// Find a number "i" that can divide "n"
		for (i = (long) Math.sqrt(n); n % i != 0 && i >= 1; i--) {
		}
		// If such a number "i" is found, n is not prime. Otherwise, n is prime.
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}
}
