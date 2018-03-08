package fr.rhaz.os.logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class Logger {
	
	private String title = "";
	private Logger parent = null;
	private ArrayList<Output<?>> outputs = new ArrayList<>();
	private Predicate<String> filter;
	private Function<String, String> formatter;

	public Logger(String title, Output<?> out) {
		this.title = title;
		this.outputs = new ArrayList<>(Arrays.asList(new Output<?>[] {out}));
	}
	
	public Logger(String title, Output<?>... outputs) {
		this.title = title;
		this.outputs = new ArrayList<>(Arrays.asList(outputs));
	}
	
	public Logger(String title, Logger parent) {
		this.title = title;
		this.parent = parent;
	}
	
	public Logger(String title, Logger parent, Output<?> out) {
		this.title = title;
		this.parent = parent;
		this.outputs = new ArrayList<>(Arrays.asList(new Output<?>[] {out}));
	}
	
	public Logger(String title, Logger parent, Output<?>... outputs) {
		this.title = title;
		this.parent = parent;
		this.outputs = new ArrayList<>(Arrays.asList(outputs));
	}
	
	public boolean hasParent() {
		return this.parent != null;
	}
	
	public void write(String msg) {
		
		if(filter(msg)) return;
			
		msg = format(msg);
		
		if(hasParent())
			parent.write(msg);
		
		for(Output<?> out:outputs)
			out.write(msg);
	}
	
	public ArrayList<Output<?>> getOutputs(){
		return outputs;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setFormat(Function<String, String> formatter) {
		this.formatter = formatter;
	}
	
	public String format(String msg) {
		return formatter.apply(msg);
	}
	
	public boolean filter(String msg) {
		if(filter == null) return false;
		
		return filter.test(msg);
	}
	
	public void setFilter(Predicate<String> filter) {
		this.filter = filter;
	}
}
