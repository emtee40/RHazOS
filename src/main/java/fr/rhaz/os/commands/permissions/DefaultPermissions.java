package fr.rhaz.os.commands.permissions;

public enum DefaultPermissions{
	
	RUN("os.cmd"),
	ALL_RUN("os.cmd.*"),
	
	SUDO("os.cmd.sudo", RUN.get()),
	SU("os.cmd.su", RUN.get())
	
	;
	
	private Permission permission;

	private DefaultPermissions(String name) {
		permission = new Permission(name);
	}
	
	private DefaultPermissions(String name, Permission... required) {
		permission = new Permission(name, required);
	}
	
	public Permission get() {
		return permission;
	}
}
