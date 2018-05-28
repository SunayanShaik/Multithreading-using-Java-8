package edu.umb.cs.cs681.hw18;

@FunctionalInterface
public interface Observer<T extends Object, U extends Object> {

	public void update(Observable obs, Object obj);

}
