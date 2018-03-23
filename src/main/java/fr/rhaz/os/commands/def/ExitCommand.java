package fr.rhaz.os.commands.def;

import fr.rhaz.os.chains.Element;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.users.User;

public class ExitCommand extends Command {
	public ExitCommand(CommandManager cman) {
		super("exit");
		addExecutor(new CommandExecutor() {
			
			@Override
			public void run(CommandLine line) throws ExecutionException {
				Element<User> user = cman.getOS().getUserElement();
				if(user.previous() != null)
					cman.getOS().setUser(user.previous());
				else throw new ExecutionException("No previous user");
			}
		});
	}
}
