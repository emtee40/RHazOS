package fr.rhaz.os.commands.arguments.def;

import java.util.Map.Entry;

import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.arguments.Parameter;

public class DashArgument<T> extends Argument<Entry<String, T>> {
	
	private String name = null;
	private Argument<T> sub = null;
	
	// Any dash argument that match given type
	public DashArgument(Argument<T> sub) {
		this.sub = sub;
	}
	
	// The dash argument with name that match given name and value that match given type
	public DashArgument(String name, Argument<T> sub) {
		this.name = name;
		this.sub = sub;
	}

	@Override
	public void check(String value) throws ArgumentException {
		String[] split = value.split("=");
		
		// Check if it is a DashArgument
		if(!split[0].startsWith("-"))
			throw new ArgumentException();
		
		// If we search for a named one or a any one
		if(name != null)
		if(!split[0].equalsIgnoreCase("-"+name))
			throw new ArgumentException();
		
		// If it has a value (for empty ones see BooleanDashArgument)
		if(split.length == 2)
			sub.check(split[1]);
	}

	@Override
	public Parameter<T> get(String value) throws ExecutionException {
		String[] split = value.split("=");
		if(split.length == 2)
			return new Parameter<T>(split[0], sub.get(split[1]));
		else return null;
	}

}
