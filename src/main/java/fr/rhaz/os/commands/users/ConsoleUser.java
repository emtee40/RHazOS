package fr.rhaz.os.commands.users;

import fr.rhaz.os.Console;
import fr.rhaz.os.commands.permissions.Permission;

public class ConsoleUser extends CommandSender {

	private Console console;
	private User user;
	
	public ConsoleUser(Console console, User user) {
		this.user = user;
		this.console = console;
	}

	@Override
	public void write(String msg) {
		getConsole().getLogger().write(msg);
	}

	public Console getConsole() {
		return console;
	}

	@Override
	public boolean has(Permission perm) {
		return user.has(perm);
	}

	@Override
	public String getName() {
		return user.getName();
	}
	
}
