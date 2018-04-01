package fr.rhaz.os.logging.def;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fr.rhaz.os.logging.Input;

public class ScannerInput extends Input<InputStream>{

	private Scanner scanner;

	public ScannerInput(InputStream in) {
		super(in);
		scanner = new Scanner(in);
	}

	@Override
	public String read() {
		try{
			return scanner.nextLine();
		} catch (NoSuchElementException | IllegalStateException e) {
			return null;
		}
	}

}
