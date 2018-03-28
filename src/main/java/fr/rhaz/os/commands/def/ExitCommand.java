package fr.rhaz.os.commands.def;

import fr.rhaz.os.Console;
import fr.rhaz.os.chains.Element;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.users.ConsoleUser;
import fr.rhaz.os.commands.users.User;

public class ExitCommand extends Command {
	public ExitCommand(final CommandManager cman) {
		super("exit");
		addExecutor(new CommandExecutor() {
			
			@Override
			public void run(CommandLine line) throws ExecutionException {
				
				if(!(line.getSender() instanceof ConsoleUser))
					throw new ExecutionException("This command cannot be executed here.");
				
				Console console = ((ConsoleUser) line.getSender()).getConsole();
				
				Element<User> user = console.getSession().getUserElement();
				if(user.previous() != null) {
					console.getSession().setUser(user.previous());
					console.getLogger().updatePrompt();
				}else throw new ExecutionException("No previous user");
			}
		});
	}
}
