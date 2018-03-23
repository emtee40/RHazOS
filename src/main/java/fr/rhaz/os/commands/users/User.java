package fr.rhaz.os.commands.users;

import java.io.File;

import fr.rhaz.os.OS;

public abstract class User extends CommandSender{
	
	private File folder;
	private OS os;
	private String name;

	public User(OS os, String name) {
		this.os = os;
		this.name = name;
		this.folder = new File("users", name);
	}
	
	public File getFolder() {
		return folder;
	}
	
	public OS getOS() {
		return os;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
