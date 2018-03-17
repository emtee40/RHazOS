package fr.rhaz.os.commands;

import java.util.ArrayList;

import fr.rhaz.os.OS;
import fr.rhaz.os.Utils;

public class CommandManager {
	
	private ConsoleSender sender;

	public CommandManager(OS os) {
		this.commands = new ArrayList<>();
		this.sender = new ConsoleSender(os);
	}
	
	public ArrayList<Command> commands;
	
	public void register(Command command) {
		commands.add(command);
	}
	
	public void run(String[] line, String raw) throws ExecutionException, PermissionException, ArgumentException {
		run(sender, line, raw);
	}
	
	public void run(CommandSender sender, String[] line, String raw) throws ExecutionException, PermissionException, ArgumentException {
	
		if(line.length == 0) return;
		
		for(Command command:commands) {
			try {
				command.check(line[0]);
				command.check(sender);
				String[] args = Utils.removeFirst(line);
				command.run(line[0], sender, args, raw);
				return;
			} catch (ArgumentException e) {}
		}
		
		throw new ArgumentException("Unknown command. Type 'help' for help.");
	}
}
