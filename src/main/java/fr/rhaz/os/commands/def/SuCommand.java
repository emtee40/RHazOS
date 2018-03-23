package fr.rhaz.os.commands.def;

import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.def.StringArgument;
import fr.rhaz.os.commands.permissions.Permission;
import fr.rhaz.os.commands.permissions.PermissionException;

public class SuCommand extends Command{
	
	private Permission permission;

	public SuCommand(CommandManager cman) {
		super("su");
		this.permission = new Permission("os.cmd.su");
		addExecutor(new CommandExecutor(new StringArgument()) {
			
			@Override
			public void run(CommandLine line) throws ExecutionException {
				String user = line.read(new StringArgument()).get();
				try{
					cman.getOS().su(user);
				} catch(NullPointerException ex) {
					throw new ExecutionException(ex.getMessage());
				}
			}
			
			@Override
			public void check(CommandLine line) throws PermissionException {
				if(!line.getSender().has(permission))
					throw new PermissionException("You do not have permission");
			}
		});
	}
}
