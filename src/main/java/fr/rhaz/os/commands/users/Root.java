package fr.rhaz.os.commands.users;

import fr.rhaz.os.commands.permissions.Permission;

public class Root extends User {

	public Root() {
		super("root");
	}

	@Override
	public boolean has(Permission perm) {
		return true;
	}

}
