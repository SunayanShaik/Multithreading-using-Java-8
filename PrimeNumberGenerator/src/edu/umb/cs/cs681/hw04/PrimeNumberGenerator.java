package edu.umb.cs.cs681.hw04;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberGenerator implements Runnable {
	protected long from, to;
	protected List<Long> primes;

	public PrimeNumberGenerator(long from, long to) {
		if (from >= 1 && to >= from) {
			this.primes = new ArrayList<Long>();
			this.from = from;
			this.to = to;
		} else {
			throw new RuntimeException("Wrong input values: from=" + from + " to=" + to);
		}
	}

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

	public void run() {
		for (long n = from; n <= to; n++) {
			if (isPrime(n)) {
				this.primes.add(n);
			}
		}
	}
}
