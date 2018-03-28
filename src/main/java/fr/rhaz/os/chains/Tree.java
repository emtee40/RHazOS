package fr.rhaz.os.chains;

import java.util.ArrayList;
import java.util.List;

import fr.rhaz.os.Utils;

public class Tree<T> {
	
	private final T parent;
	private final List<T> children;

	public Tree(T parent) {
		this.parent = parent;
		this.children = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public Tree(T parent, T... children) {
		this.parent = parent;
		this.children = Utils.list(children);
	}
	
	public final T getParent() {
		return parent;
	}
	
	public final List<T> getChildren(){
		return children;
	}
	
	public boolean contains(T test) {
		if(test.equals(parent)) return true;
		if(children.contains(test)) return true;
		return false;
	}
}
