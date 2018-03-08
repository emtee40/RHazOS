package fr.rhaz.os.commands;

import java.util.ArrayList;

import fr.rhaz.os.Utils;

public class CommandManager {
	
	private ConsoleSender sender;

	public CommandManager() {
		this.commands = new ArrayList<>();
		this.sender = new ConsoleSender();
	}
	
	public ArrayList<Command> commands;
	
	public void register(Command command) {
		commands.add(command);
	}
	
	public void run(String[] line) throws ExecutionException, PermissionException, ArgumentException {
		run(sender, line);
	}
	
	public void run(CommandSender sender, String[] line) throws ExecutionException, PermissionException, ArgumentException {
		if(line.length == 0) return;
		
		for(Command command:commands) {
			try {
				command.check(line[0]);
				command.check(sender);
				line = Utils.removeFirst(line);
				command.run(sender, line);
				return;
			} catch (ArgumentException e) {}
		}
		
		throw new ArgumentException("Unknown command. Type 'help' for help.");
	}
}
