package fr.rhaz.os.commands.arguments.def;

import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;

public class IntegerArgument extends Argument<Integer> {

	public static IntegerArgument i() {
		return new IntegerArgument();
	}
	
	@Override
	public void check(String value) throws ArgumentException {
		try {
			Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ArgumentException();
		}
	}

	@Override
	public Integer get(String value) throws ExecutionException {
		return Integer.valueOf(value);
	}

}
