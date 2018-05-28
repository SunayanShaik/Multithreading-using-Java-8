package edu.umb.cs.cs681.hw13;

import edu.umb.cs.cs681.hw13.ThreadSafeBankAccount2.DepositRunnable;
import edu.umb.cs.cs681.hw13.ThreadSafeBankAccount2.WithdrawRunnable;

public class ThreadSafeBankAccount2Demo {

	public static void main(String[] args) {
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
		DepositRunnable depositRunnable = bankAccount.new DepositRunnable();
		WithdrawRunnable withdrawRunnable = bankAccount.new WithdrawRunnable();
		Thread depositThread = new Thread(depositRunnable);
		Thread withdrawThread = new Thread(withdrawRunnable);
		depositThread.start();
		withdrawThread.start();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage());
			e.printStackTrace();
		}
		
		depositThread.interrupt();
		withdrawThread.interrupt();
		
		depositRunnable.setDone();
		withdrawRunnable.setDone();
	}

}
