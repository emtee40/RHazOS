package fr.rhaz.os.commands.arguments;

import fr.rhaz.os.commands.Argument;
import fr.rhaz.os.commands.ArgumentException;
import fr.rhaz.os.commands.ExecutionException;

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
