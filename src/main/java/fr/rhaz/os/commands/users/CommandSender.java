package fr.rhaz.os.commands.users;

import fr.rhaz.os.commands.permissions.Permission;

public abstract class CommandSender {
	public abstract boolean has(Permission perm);
	
	public abstract void write(String msg);
	
	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> type) {
		return getClass().isAssignableFrom(type)? (T) this: null;
	}
	
	public abstract String getName();
}
