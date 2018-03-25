package fr.rhaz.os.logging.def;

import java.util.NoSuchElementException;
import java.util.Scanner;

import fr.rhaz.os.logging.Input;

public class SystemInput extends Input<Scanner>{

	public SystemInput() {
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
