package fr.rhaz.os.commands;

public class ConsoleSender implements CommandSender{

	@Override
	public boolean hasPermission(Permission perm) {
		return true;
	}
	
}
