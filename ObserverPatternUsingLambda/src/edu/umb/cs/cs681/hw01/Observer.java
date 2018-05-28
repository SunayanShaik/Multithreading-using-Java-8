package edu.umb.cs.cs681.hw01;

@FunctionalInterface
public interface Observer<T extends Object, U extends Object> {

	public void update(Observable obs, Object obj);

}