package edu.umb.cs.cs681.hw04;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class StreamBasedPrimeNumberGenerator extends PrimeNumberGenerator {

	public StreamBasedPrimeNumberGenerator(long from, long to) {
		super(from, to);
	}

	public void run() {
		this.primes = LongStream.rangeClosed(this.from, this.to).filter(n->isPrime(n)).boxed().collect(Collectors.toList());
	}

}
