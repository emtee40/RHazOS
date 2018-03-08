package fr.rhaz.os.commands;

public class PermissionException extends Exception {
	
	private static final long serialVersionUID = -249896030074942885L;

	public PermissionException() {
		super();
	}
	
	public PermissionException(String msg) {
		super(msg);
	}
}
