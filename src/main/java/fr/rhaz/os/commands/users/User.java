package fr.rhaz.os.commands.users;

import java.io.File;

import fr.rhaz.os.commands.permissions.Permission;

public abstract class User{
	
	private File folder;
	private String name;
	
	public User(String name) {
		this.name = name;
		this.folder = new File("users", name);
	}
	
	public File getFolder() {
		return folder;
	}
	
	public String getName() {
		return name;
	}

	public abstract boolean has(Permission perm);
}
