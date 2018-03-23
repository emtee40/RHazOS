package fr.rhaz.os.commands.def;

import java.util.ArrayList;
import java.util.Collections;

import fr.rhaz.os.Utils;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandExecutor;
import fr.rhaz.os.commands.CommandLine;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.permissions.PermissionException;

public class HelpCommand extends Command{

	public HelpCommand(final CommandManager cman) {
		super("help");
		setDescription("Help");
		addExecutor(new CommandExecutor() {
	
			@Override
			public void run(CommandLine line) throws ExecutionException {
				ArrayList<String> list = new ArrayList<>();
				for(Command cmd:cman.getCommands()) {
					try {
						cmd.check(line.getSender());
						
						ArrayList<CommandExecutor> exs = new ArrayList<>(cmd.getExecutors());
						Collections.reverse(exs);
						
						for(CommandExecutor ex:exs) {
							try {
								ex.check(line);
								list.add(
									Utils.join(",", cmd.getAliases())
									+(ex.toString().equals("")?"":(" "+ex))
									+(ex.getDescription().isEmpty()?
										(ex.getArguments().length==0?
												(cmd.getDescription().isEmpty()?"":(": "+cmd.getDescription())):"")
									:(": "+ex.getDescription()))
								);
							} catch(PermissionException e2) {}
						}
					} catch (PermissionException e) {}
				}
				
				if(list.isEmpty()) {
					line.getSender().write("There is not any command.");
					return;
				}
				
				line.getSender().write("\n		Help\n");
				for(String msg:list) line.getSender().write(msg);
			}
		});
	}
}
