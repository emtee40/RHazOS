package fr.rhaz.os.commands;

import java.util.Arrays;
import java.util.List;

import fr.rhaz.os.Utils;

public class Command {
	private String[] aliases = null;
	private Type type;
	private CommandExecutor[] executors = null;
	private Command[] subcommands = null;

	public Command(String[] aliases) {
		this.type = Type.COMMAND;
		this.aliases = aliases;
	}
	
	public Command(CommandExecutor[] executors, String[] aliases) {
		this.type = Type.COMMAND;
		this.executors = executors;
		this.aliases = aliases;
	}
	
	public Command(Command[] subcommands, String[] aliases) {
		this.type = Type.NODE;
		this.subcommands = subcommands;
		this.aliases = aliases;
	}
	
	public void check(String name) throws ArgumentException {
		List<String> list = Arrays.asList(aliases);
		if(!list.contains(name)) throw new ArgumentException();
	}
	
	public void check(CommandSender sender) throws PermissionException{}
	
	public void run(CommandLine line) throws ExecutionException{}
	
	public void run(CommandSender sender, String[] line) throws ExecutionException, PermissionException, ArgumentException{
		switch(type) {
			case NODE:{
				
				for(Command child:subcommands) {
					try {
						child.check(line[0]);
						child.check(sender);
						line = Utils.removeFirst(line);
						child.run(sender, line);
						return;
					} catch (ArgumentException e) {}
				}
				
				throw new ArgumentException();
			}
			case COMMAND:{
				
				if(executors == null) {
					run(new CommandLine(line, new Argument[] {}));
					return;
				}
				
				for(CommandExecutor exec:executors) {
					try {
						exec.check(line);
						exec.check(sender);
						exec.run(new CommandLine(line, exec.getArguments()));
						return;
					} catch (ArgumentException e) {}
				}
				
				throw new ArgumentException();
			}
		}
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public Type getType() {
		return type;
	}

	public static enum Type {
		NODE,
		COMMAND;
	}
}
