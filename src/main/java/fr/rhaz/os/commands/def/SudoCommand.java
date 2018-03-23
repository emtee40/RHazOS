package fr.rhaz.os.commands.def;

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

public class SudoCommand extends Command {
	
	private Permission permission;

	public SudoCommand(final CommandManager cman) {
		super("sudo");
		this.permission = new Permission("os.cmd.sudo");
		addExecutor(new CommandExecutor() {
			
			@Override
			public void run(CommandLine line) throws ExecutionException {
				String user = line.read(new StringArgument()).get();
				try{
					try {
						cman.run(cman.getOS().getUser(user), Utils.removeFirst(line.getLine()), line.getRawLine());
					} catch (ArgumentException | PermissionException e) {
						line.getSender().write(e.getMessage());
					}
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
