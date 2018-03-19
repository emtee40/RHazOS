package fr.rhaz.os.commands;

import java.util.ArrayList;
import java.util.Collections;

import fr.rhaz.os.Utils;

public class HelpCommandExecutor extends CommandExecutor {
	
	private Command command;
	private String header;

	public HelpCommandExecutor(Command command) {
		this.command = command;
		this.setDescription("Help for command \""+command.getAliases().get(0)+"\"");
		this.header = "\n		"+getDescription()+"\n";
	}
	
	@Override
	public void run(CommandLine line) throws ExecutionException {
		
		ArrayList<String> list = new ArrayList<>();
		
		ArrayList<CommandExecutor> exs = new ArrayList<>(command.getExecutors());
		Collections.reverse(exs);
		
		for(CommandExecutor ex:exs) {
			try {
				ex.check(line);
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
						subex.check(line);
						list.add(
							line.getAlias()+" "
							+Utils.join(",", sub.getAliases())
							+(subex.toString().equals("")?"":(" "+subex))
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
		
		line.getSender().write(header);
		for(String msg:list) line.getSender().write(msg);
	}
	
	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public void check(CommandLine line) throws PermissionException {
		// TODO Auto-generated method stub
		
	}
}
