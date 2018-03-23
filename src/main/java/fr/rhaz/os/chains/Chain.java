package fr.rhaz.os.chains;

public class Chain<T>{
	
	private Element<T> base;
	
	public Chain(T base){
		this.base = new Element<T>(base);
	}
	
	public Element<T> getBase() {
		return base;
	}
}
