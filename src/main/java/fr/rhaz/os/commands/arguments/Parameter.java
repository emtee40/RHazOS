package fr.rhaz.os.commands.arguments;

import java.util.Map.Entry;

public class Parameter<T> implements Entry<String, T> {

	private String key = "";
	private T value;
	
	public Parameter(String key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public Parameter(T value) {
		this.value = value;
	}
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public T getValue() {
		return value;
	}
	
	public String setKey(String key) {
		String old = this.key;
		this.key = key;
		return old;
	}

	@Override
	public T setValue(T value) {
		T old = this.value;
		this.value = value;
		return old;
	}
	
	@Override
	public String toString() {
		return key.isEmpty()?value.toString():(key+"="+value.toString());
	}

}
