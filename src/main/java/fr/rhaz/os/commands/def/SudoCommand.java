package fr.rhaz.os.commands.def;

import fr.rhaz.os.Console;
import fr.rhaz.os.Utils;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.arguments.def.StringArgument;
import fr.rhaz.os.commands.permissions.Permission;
import fr.rhaz.os.commands.permissions.PermissionException;
import fr.rhaz.os.commands.users.ConsoleUser;

public class SudoCommand extends Command {
	
	private Permission permission;

	public SudoCommand(final CommandManager cman) {
		super("sudo");
		this.permission = new Permission("os.cmd.sudo");
		addExecutor(new CommandExecutor(new StringArgument(), new StringArgument()) {
			
			@Override
			public void run(CommandLine line) throws ExecutionException, PermissionException, ArgumentException {
				String user = line.read(new StringArgument()).get();
				
				if(!(line.getSender() instanceof ConsoleUser))
					throw new ExecutionException("This command cannot be executed here.");
				
				Console console = ((ConsoleUser) line.getSender()).getConsole();
				
				cman.run(console.getUser(user), Utils.removeFirst(line.getLine()), line.getRawLine());
			}
			
			@Override
			public String toString() {
				return "<user> <command>";
			}
			
			@Override
			public void check(CommandLine line) throws PermissionException {
				if(!line.getSender().has(permission))
					throw new PermissionException("You do not have permission");
			}
		});
	}
}
