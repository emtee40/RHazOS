package fr.rhaz.os.commands;

public abstract class Argument<T> {
	
	public String toString() {
		return "<arg>";
	}
	
	public abstract void check(String value) throws ArgumentException;
	
	public abstract T get(String value) throws ExecutionException;
}
