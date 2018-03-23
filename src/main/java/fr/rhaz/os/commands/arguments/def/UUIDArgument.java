package fr.rhaz.os.commands.arguments.def;

import java.util.UUID;

import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;

public class UUIDArgument extends Argument<UUID> {

	public static UUIDArgument i() {
		return new UUIDArgument();
	}
	
	@Override
	public void check(String value) throws ArgumentException {
		try {
			UUID.fromString(value);
		} catch (IllegalArgumentException e) {
			throw new ArgumentException();
		}
	}

	@Override
	public UUID get(String value) throws ExecutionException {
		return UUID.fromString(value);
	}
	
}
