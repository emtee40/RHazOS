package fr.rhaz.os.commands.def;

import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.ExecutionException;

public class WhoAmICommand extends Command {
	public WhoAmICommand() {
		super("whoami");
		addExecutor(new CommandExecutor() {
			@Override
			public void run(CommandLine line) throws ExecutionException {
				line.getSender().write(line.getSender().getName());
			}
		});
	}
}
