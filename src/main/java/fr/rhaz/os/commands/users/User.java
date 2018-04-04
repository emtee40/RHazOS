package fr.rhaz.os.commands.users;

import java.io.File;

import fr.rhaz.os.OS;
import fr.rhaz.os.commands.permissions.Permission;

public abstract class User{
	
	private File folder;
	private String name;
	private OS os;
	
	public User(OS os, String name) {
		this.os = os;
		this.name = name;
		this.folder = new File(os.getUsersFolder(), name);
	}
	
	public OS getOS() {
		return os;
	}
	
	public File getFolder() {
		return folder;
	}
	
	public String getName() {
		return name;
	}

	public abstract boolean has(Permission perm);
}
