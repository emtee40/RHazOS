package fr.rhaz.os.commands.arguments;

import fr.rhaz.os.commands.Argument;
import fr.rhaz.os.commands.ExecutionException;

public class StringArgument extends Argument<String> {
	
	public static StringArgument i() {
		return new StringArgument();
	}

	@Override
	public void check(String value) {}

	@Override
	public String get(String value) throws ExecutionException {
		return value;
	}
	
}
