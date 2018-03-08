package fr.rhaz.os.commands.arguments;

import java.util.UUID;

import fr.rhaz.os.commands.Argument;
import fr.rhaz.os.commands.ArgumentException;
import fr.rhaz.os.commands.ExecutionException;

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
