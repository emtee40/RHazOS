package fr.rhaz.os.commands;

import java.util.ArrayList;
import java.util.Collections;

import fr.rhaz.os.Utils;

public class HelpCommandExecutor extends CommandExecutor {
	
	private Command command;

	public HelpCommandExecutor(Command command) {
		this.command = command;
	}
	
	@Override
	public void run(CommandLine line) throws ExecutionException {
		
		if(this.getDescription().isEmpty())
			this.setDescription("Help for command \""+line.getAlias()+"\":");
		
		ArrayList<String> list = new ArrayList<>();
		
		ArrayList<CommandExecutor> exs = new ArrayList<>(command.getExecutors());
		Collections.reverse(exs);
		
		for(CommandExecutor ex:exs) {
			try {
				ex.check(line.getSender());
				if(ex.getArguments().length==0) continue;
				String l = line.getAlias()+ " " + (ex.toString().equals("")?"":ex)+(ex.getDescription().isEmpty()?"":(": "+ex.getDescription()));
				list.add(l);
			} catch(PermissionException e2) {}
		}
		
		for(Command sub:command.getSubcommands()) {
			try {
				sub.check(line.getSender());
				for(CommandExecutor subex:sub.getExecutors()) {
					try {
						subex.check(line.getSender());
						list.add(
							line.getAlias()+" "
							+(subex.toString().equals("")?"":(subex+" "))
							+Utils.join(",", sub.getAliases())
							+(subex.getDescription().isEmpty()?
								(subex.getArguments().length==0?
										(sub.getDescription().isEmpty()?"":(": "+sub.getDescription())):"")
							:(": "+subex.getDescription()))
						);
					} catch(PermissionException e2) {}
				}
			} catch (PermissionException e) {}
		}
		
		if(list.isEmpty()) {
			line.getSender().write("This command does not have any subcommand or arguments.");
			return;
		}
		
		line.getSender().write(this.getDescription());
		for(String msg:list) line.getSender().write(msg);
	}
	
	@Override
	public void check(CommandSender sender) throws PermissionException {}
}
