package fr.rhaz.os.commands.arguments.def;

import java.util.Arrays;
import java.util.List;

import fr.rhaz.os.Utils;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;

public class BooleanArgument extends Argument<Boolean> {
	
	public static BooleanArgument i() {
		return new BooleanArgument();
	}
	
	@Override
	public void check(String value) throws ArgumentException {
		List<String> allowed = Utils.list("true", "false", "yes", "no");
		if(!allowed.contains(value)) throw new ArgumentException();
	}

	@Override
	public Boolean get(String value) throws ExecutionException {
		List<String> yes = Utils.list("true", "yes");
		List<String> no = Utils.list("true", "yes");
		if(yes.contains(value)) return true;
		else if(no.contains(value)) return false;
		throw new ExecutionException("Could not cast \""+value+"\" to boolean.");
	}

}
