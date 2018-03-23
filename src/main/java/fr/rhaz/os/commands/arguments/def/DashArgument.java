package fr.rhaz.os.commands.arguments.def;

import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;

public class DashArgument<T> extends Argument<T> {
	
	private String name;
	private Argument<T> sub;
	
	public DashArgument(String name, Argument<T> sub) {
		this.name = name;
		this.sub = sub;
	}

	@Override
	public void check(String value) throws ArgumentException {
		String[] split = value.split("=");
		
		if(!split[0].equalsIgnoreCase("-"+name))
			throw new ArgumentException();
		
		if(split.length == 2)
			sub.check(split[1]);
	}

	@Override
	public T get(String value) throws ExecutionException {
		String[] split = value.split("=");
		if(split.length == 2)
			return sub.get(split[1]);
		else return null;
	}

}
