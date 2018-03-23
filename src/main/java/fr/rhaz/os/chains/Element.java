package fr.rhaz.os.chains;

public class Element<T> {
	
	private T value;
	private Element<T> parent;
	private Element<T> child;

	public Element(T value) {
		this.value = value;	
	}
	
	public Element(Element<T> parent, T value) {
		this.value = value;	
		this.parent = parent;
		parent.setChild(this);
	}
	
	private void setChild(Element<T> child) {
		this.child = child;
	}
	
	public Element<T> next(){
		return child;
	}
	
	public Element<T> previous(){
		return parent;
	}
	
	public T value() {
		return value;
	}
}
