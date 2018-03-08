package fr.rhaz.os.commands;

public class ArgumentException extends Exception {
	
	private static final long serialVersionUID = 2641210766352250614L;
	
	public ArgumentException() {
		super();
	}
	
	public ArgumentException(String msg) {
		super(msg);
	}

}
