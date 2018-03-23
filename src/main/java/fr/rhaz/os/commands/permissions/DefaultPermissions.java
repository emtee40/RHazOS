package fr.rhaz.os.commands.permissions;

public enum DefaultPermissions{
	RUN("rhazos.run"),
	SUDO("rhazos.run.sudo", RUN.get()),
	ALL_RUN("rhazos.run.*")
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
