package fr.rhaz.os.logging;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInput extends Input<Scanner>{

	public ConsoleInput() {
		super(new Scanner(System.in));
	}

	@Override
	public String read() {
		try{
			return in.nextLine();
		} catch (NoSuchElementException | IllegalStateException e) {
			return null;
		}
	}

}
