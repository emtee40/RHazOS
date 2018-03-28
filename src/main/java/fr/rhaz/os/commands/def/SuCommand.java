package fr.rhaz.os.commands.def;

import fr.rhaz.os.Console;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.def.StringArgument;
import fr.rhaz.os.commands.permissions.Permission;
import fr.rhaz.os.commands.permissions.PermissionException;
import fr.rhaz.os.commands.users.ConsoleUser;

public class SuCommand extends Command{
	
	private Permission permission;

	public SuCommand(final CommandManager cman) {
		super("su");
		this.permission = new Permission("os.cmd.su");
		addExecutor(new CommandExecutor(new StringArgument()) {
			
			@Override
			public void run(CommandLine line) throws ExecutionException {
				String user = line.read(new StringArgument()).get();
				
				if(!(line.getSender() instanceof ConsoleUser))
					throw new ExecutionException("This command cannot be executed here.");
				
				Console console = ((ConsoleUser) line.getSender()).getConsole();
				
				console.getSession().su(user);
				
				console.getLogger().updatePrompt();
			}
			
			@Override
			public String toString() {
				return "<user>";
			}
			
			@Override
			public void check(CommandLine line) throws PermissionException {
				if(!line.getSender().has(permission))
					throw new PermissionException("You do not have permission");
			}
		});
	}
}
