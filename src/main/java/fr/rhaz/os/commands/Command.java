package fr.rhaz.os.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.rhaz.os.Utils;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.permissions.PermissionException;
import fr.rhaz.os.commands.users.CommandSender;

public class Command {
	private List<String> aliases;
	private List<CommandExecutor> executors;
	private List<Command> subcommands;
	private String desc;
	
	public Command() {
		this.aliases = new ArrayList<>();
		this.executors = new ArrayList<>();
		this.subcommands = new ArrayList<>();
		this.desc = "";
	}
	
	public Command(List<String> aliases) {
		this.aliases = aliases;
		this.executors = new ArrayList<>();
		this.subcommands = new ArrayList<>();
		this.desc = "";
	}
	
	public Command(String... aliases) {
		this.aliases = Utils.list(aliases);
		this.executors = new ArrayList<>();
		this.subcommands = new ArrayList<>();
		this.desc = "";
	}
	
	public Command setDescription(String description) {
		desc = description;
		return this;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public Command addAlias(String alias) {
		aliases.add(alias);
		return this;
	}
	
	public Command addExecutor(CommandExecutor executor) {
		executors.add(executor);
		sortExecutors();
		return this;
	}
	
	public HelpCommandExecutor addHelpExecutor() {
		HelpCommandExecutor hce = new HelpCommandExecutor(this);
		this.addExecutor(hce);
		return hce;
	}
	
	public Command addSubcommand(Command command) {
		subcommands.add(command);
		return this;
	}
	
	public List<String> getAliases() {
		return aliases;
	}
	
	public List<CommandExecutor> getExecutors(){
		return executors;
	}
	
	public List<Command> getSubcommands(){
		return subcommands;
	}
	
	public void check(String name) throws ArgumentException {
		if(!aliases.contains(name)) throw new ArgumentException();
	}
	
	private void sortExecutors() {
		Collections.sort(executors, new Comparator<CommandExecutor>() {

			@Override
			public int compare(CommandExecutor e1, CommandExecutor e2) {
				int a1 = e1.getArguments().length;
				int a2 = e2.getArguments().length;
				return Integer.compare(a2, a1);
			}
			
		});
	}
	
	public void check(CommandSender sender) throws PermissionException{}
	
	public void run(CommandLine line) throws ExecutionException{}
	
	public void run(String alias, CommandSender sender, String[] line, String raw) throws ExecutionException, PermissionException, ArgumentException{
		
		commands:{
			if(line.length == 0) break commands;
			for(Command child:subcommands) {
				try {
					child.check(line[0]);
					child.check(sender);
					String[] args = Utils.removeFirst(line);
					child.run(line[0], sender, args, raw);
					return;
				} catch (ArgumentException e) {}
			}
		}
		
		for(CommandExecutor exec:executors) {
			try {
				CommandLine cline = new CommandLine(alias, this, sender, line, exec.getArguments(), raw);
				exec.check(line);
				exec.check(cline);
				exec.run(cline);
				return;
			} catch (ArgumentException e) {}
		}
		
		throw new ArgumentException();
	}
	
}
