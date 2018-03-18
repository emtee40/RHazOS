package fr.rhaz.os.commands;

import java.util.ArrayList;
import java.util.Collections;

import fr.rhaz.os.OS;
import fr.rhaz.os.Utils;

public class CommandManager {
	
	private ConsoleSender sender;
	private Command help;

	public CommandManager(OS os) {
		this.commands = new ArrayList<>();
		this.sender = new ConsoleSender(os);
		
		this.help = new Command("help").setDescription("Help")
				.addExecutor(new CommandExecutor() {
			
			@Override
			public void run(CommandLine line) throws ExecutionException {
				ArrayList<String> list = new ArrayList<>();
				for(Command cmd:commands) {
					try {
						cmd.check(line.getSender());
						
						ArrayList<CommandExecutor> exs = new ArrayList<>(cmd.getExecutors());
						Collections.reverse(exs);
						
						for(CommandExecutor ex:exs) {
							try {
								ex.check(line.getSender());
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
			
			@Override
			public void check(CommandSender sender) throws PermissionException {}
		});
		register(help);
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
