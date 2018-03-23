package fr.rhaz.os.commands.arguments.def;

import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;

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
