package fr.rhaz.os.commands.users;

import fr.rhaz.os.OS;
import fr.rhaz.os.commands.permissions.Permission;

public class Root extends ConsoleUser{

	public Root(OS os) {
		super(os, "root");
	}
	
	@Override
	public boolean has(Permission perm) {
		return true;
	}

}
