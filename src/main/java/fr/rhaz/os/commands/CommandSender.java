package fr.rhaz.os.commands;

public abstract class CommandSender {
	public abstract boolean hasPermission(Permission perm);
	
	public abstract void write(String msg);
	
	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> type) {
		return this.getClass().isAssignableFrom(type)? null:(T) this;
	}
}
