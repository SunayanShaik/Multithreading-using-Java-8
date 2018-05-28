package edu.umb.cs.cs681.hw13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeBankAccount2 {
	private ThreadSafeBankAccount2 account;
	private double balance = 0;
	private ReentrantLock lock;
	private boolean done = false;
	Condition sufficientFundsCondition, belowUpperLimitFundsCondition; 

	public ThreadSafeBankAccount2() {
		account = this;
		this.lock = new ReentrantLock();
		sufficientFundsCondition = lock.newCondition();
		belowUpperLimitFundsCondition = lock.newCondition();
	}

	public void deposit(double amount) {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " deposit()]" + " Lock obtained");
		while (balance >= 300) {
			// Wait for the balance to go below 300.
			try {
				belowUpperLimitFundsCondition.await();
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage() + " by signalAll()");
				break;
			}
		}
		balance += amount;
		sufficientFundsCondition.signalAll();
		System.out.println("Balance after deposit(100) is : " + balance);
		lock.unlock();
		System.out.println("[" + Thread.currentThread().getName() + " deposit()]" + " Lock released");
	}

	public void withdraw(double amount) {
		lock.lock();
		System.out.println("[" + Thread.currentThread().getName() + " withdraw()]" + " Lock obtained");
		while (balance <= 0) {
			// Wait for the balance to exceed 0
			try {
				sufficientFundsCondition.await();
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] " + e.getMessage() + " by signalAll()");
				break;
			}
		}
		balance -= amount;
		System.out.println("Balance after withdraw(100) is : " + balance);
		belowUpperLimitFundsCondition.signalAll();
		lock.unlock();
		System.out.println("[" + Thread.currentThread().getName() + " withdraw()]" + " Lock released");
	}

	public class DepositRunnable implements Runnable {
		public void run() {
			while (true) {
				try {
					lock.lock();
					System.out.println(
							"[" + Thread.currentThread().getName() + " DepositRunnable - run()]" + " Lock obtained");
					account.deposit(100);
					Thread.sleep(5);
					if (done == true) {
						break;
					}
				} catch (InterruptedException exception) {
					System.out.println("[" + Thread.currentThread().getName() + "] " + exception.getMessage());
				} finally {
					lock.unlock();
					System.out.println(
							"[" + Thread.currentThread().getName() + " DepositRunnable - run()]" + " Lock released");
				}
			}
		}
		
		public void setDone() {
			lock.lock();
			try {
				System.out.println("[" + Thread.currentThread().getName() + " DepositRunnable - setDone()]" + " Lock obtained");
				done = true;
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " DepositRunnable - setDone()]" + " Lock released");
			}		
		}
	}

	public class WithdrawRunnable implements Runnable {
		public void run() {
			while (true) {
				try {
					lock.lock();
					System.out.println(
							"[" + Thread.currentThread().getName() + " WithdrawRunnable - run()]" + " Lock obtained");
					account.withdraw(100);
					Thread.sleep(5);
					if (done == true) {
						break;
					}
				} catch (InterruptedException exception) {
					System.out.println("[" + Thread.currentThread().getName() + "] " + exception.getMessage());
				} finally {
					lock.unlock();
					System.out.println(
							"[" + Thread.currentThread().getName() + " WithdrawRunnable - run()]" + " Lock released");
				}
			}
		}
		
		public void setDone() {
			lock.lock();
			try {
				System.out.println("[" + Thread.currentThread().getName() + " WithdrawRunnable - setDone()]" + " Lock obtained");
				done = true;
			} finally {
				lock.unlock();
				System.out.println("[" + Thread.currentThread().getName() + " WithdrawRunnable - setDone()]" + " Lock released");
			}		
		}
	}
}
