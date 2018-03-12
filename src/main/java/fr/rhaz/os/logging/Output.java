package fr.rhaz.os.logging;

public abstract class Output<T> {
	public T out;
	
	public Output(T out){
		this.out = out;
	}
	
	public void set(T out) {
		this.out = out;
	}
	
	public T get() {
		return out;
	}
	
	public abstract void write(String msg);
}
