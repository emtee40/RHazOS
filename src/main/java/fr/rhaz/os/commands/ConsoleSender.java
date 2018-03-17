package fr.rhaz.os.commands;

import fr.rhaz.os.OS;

public class ConsoleSender extends CommandSender{
	
	private OS os;

	public ConsoleSender(OS os) {
		this.os = os;
	}

	@Override
	public boolean hasPermission(Permission perm) {
		return true;
	}

	@Override
	public void write(String msg) {
		os.write(msg);
	}
	
}
