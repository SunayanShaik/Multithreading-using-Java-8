package edu.umb.cs.cs681.hw10.part1;

public class SecurityGateAndGuestDemo_Part1 {

	public static void main(String[] args) throws InterruptedException {
		
		Guest guestRunnable1 = new Guest();
		Guest guestRunnable2 = new Guest();
		Guest guestRunnable3 = new Guest();
		
		Thread guestThread1 = new Thread(guestRunnable1);
		Thread guestThread2 = new Thread(guestRunnable2);
		Thread guestThread3 = new Thread(guestRunnable3);
		
		guestThread1.start();
		guestThread1.join();
		
		guestThread2.start();
		guestThread2.join();
		
		guestThread3.start();
		guestThread3.join();
		
	}

}
