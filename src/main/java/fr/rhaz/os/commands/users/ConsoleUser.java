package fr.rhaz.os.commands.users;

import java.util.HashSet;

import fr.rhaz.os.OS;
import fr.rhaz.os.commands.permissions.Permission;
import fr.rhaz.os.commands.permissions.PermissionManager;

public class ConsoleUser extends User {

	private HashSet<Permission> permissions;

	public ConsoleUser(OS os, String name, Permission... perms) {
		super(os, name);
		this.permissions = PermissionManager.calculate(perms);
	}
	
	public ConsoleUser(OS os, String name, String... perms) {
		super(os, name);
		this.permissions = PermissionManager.calculate(PermissionManager.from(perms));
	}
	
	public ConsoleUser(OS os, String name) {
		super(os, name);
		this.permissions = new HashSet<Permission>();
	}

	@Override
	public void write(String msg) {
		getOS().write(msg);
	}

	@Override
	public boolean has(Permission perm) {
		return PermissionManager.has(permissions, perm);
	}
	
}
