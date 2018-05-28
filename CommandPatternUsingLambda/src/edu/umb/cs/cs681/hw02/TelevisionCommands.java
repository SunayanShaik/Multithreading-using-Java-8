package edu.umb.cs.cs681.hw02;

import java.util.ArrayList;
import java.util.List;

public class TelevisionCommands {

	private final List<Command> actions;
	
	public TelevisionCommands() {
		actions = new ArrayList<>();
	}
	
	public void recordAction(Command command) {
		actions.add(command);
	}
	
	public void executeCommands() {
		actions.forEach(Command :: execute);
	}
	
}