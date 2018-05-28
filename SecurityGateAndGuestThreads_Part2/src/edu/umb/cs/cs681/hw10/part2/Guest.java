package edu.umb.cs.cs681.hw10.part2;

public class Guest implements Runnable {
	private SecurityGate gate;

	public Guest() {
		gate = SecurityGate.getInstance();
	}

	public void run() {
		gate.enter();
		gate.exit();
		gate.getCount();
	}
}
