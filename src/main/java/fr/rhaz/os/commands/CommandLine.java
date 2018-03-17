package fr.rhaz.os.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CommandLine {
	private String[] line;
	private ArrayList<String> list;
	@SuppressWarnings("rawtypes")
	private ArrayList<Class<? extends Argument>> args;
	
	public CommandLine(String[] line, Argument<?>[] args) {
		this.line = line;
		
		this.args = new ArrayList<>();
		
		for(Argument<?> arg:args) 
			this.args.add(arg.getClass());
		
		this.list = new ArrayList<>(Arrays.asList(line));
	}
	
	public String[] getRawLine() {
		return line;
	}
	
	public <T> Optional<T> read(Argument<T> arg) throws ExecutionException{
		
		int i = args.indexOf(arg.getClass());
		
		if(i == -1) return Optional.ofNullable(null);
		
		String value;
		try{
			value = list.get(i);
		} catch (IndexOutOfBoundsException e) {
			return Optional.ofNullable(null);
		}
		
		T object = arg.get(value);
		args.remove(i);
		list.remove(i);
		return Optional.ofNullable(object);
	}

}
