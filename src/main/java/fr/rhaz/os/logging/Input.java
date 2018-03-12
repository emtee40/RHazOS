package fr.rhaz.os.logging;

public abstract class Input<T> {
	public T in;
	
	public Input(T in) {
		this.in = in;
	}
	
	public void set(T in) {
		this.in = in;
	}
	
	public T get() {
		return in;
	}
	
	public abstract String read();
}
