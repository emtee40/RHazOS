package fr.rhaz.os.commands.arguments.def;

import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;

public class BooleanDashArgument extends Argument<Boolean>{
	
	private String name;
	
	public BooleanDashArgument(String name) {
		this.name = name;
	}

	@Override
	public void check(String value) throws ArgumentException {
		if(!value.equalsIgnoreCase("--"+name))
			throw new ArgumentException();
	}

	@Override
	public Boolean get(String value) throws ExecutionException {
		if(!value.equalsIgnoreCase("--"+name))
			return null;
		return true;
	}

}
