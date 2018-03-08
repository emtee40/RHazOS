package fr.rhaz.os.commands.arguments;

import java.util.Arrays;
import java.util.List;

import fr.rhaz.os.commands.Argument;
import fr.rhaz.os.commands.ArgumentException;
import fr.rhaz.os.commands.ExecutionException;

public class BooleanArgument extends Argument<Boolean> {
	
	public static BooleanArgument i() {
		return new BooleanArgument();
	}
	
	@Override
	public void check(String value) throws ArgumentException {
		List<String> allowed = Arrays.asList(new String[] {"true", "false", "yes", "no"});
		if(!allowed.contains(value)) throw new ArgumentException();
	}

	@Override
	public Boolean get(String value) throws ExecutionException {
		List<String> yes = Arrays.asList(new String[] {"true", "yes"});
		List<String> no = Arrays.asList(new String[] {"true", "yes"});
		if(yes.contains(value)) return true;
		else if(no.contains(value)) return false;
		throw new ExecutionException("Could not cast \""+value+"\" to boolean.");
	}

}
