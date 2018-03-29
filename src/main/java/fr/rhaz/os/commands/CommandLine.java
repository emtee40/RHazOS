package fr.rhaz.os.commands;

import java.util.Arrays;
import java.util.List;

import fr.rhaz.os.Console;
import fr.rhaz.os.Utils;
import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.users.CommandSender;
import fr.rhaz.os.commands.users.ConsoleUser;
import fr.rhaz.os.java.Optional;

public class CommandLine {
	private String raw;
	private List<String> values;
	private List<Argument<?>> args;
	private CommandSender sender;
	private String[] line;
	private Command command;
	private String alias;
	
	public CommandLine(String alias, Command command, CommandSender sender, String[] line, Argument<?>[] args, String raw) {
		this.line = line;
		
		this.alias = alias;
		
		this.command = command;
		
		this.raw = raw;
		
		this.sender = sender;
		
		this.args = Utils.list(args);
		
		this.values = Utils.list(line);
	}
	
	public String getAlias() {
		return alias;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public String[] getLine() {
		return line;
	}
	
	public String getRawLine() {
		return raw;
	}
	
	public Optional<Console> getConsole(){
		Console console = null;
		if(getSender() instanceof ConsoleUser)
			console = ((ConsoleUser) getSender()).getConsole();
		return Optional.ofNullable(console);
	}
	
	// Read the next value as an arg
	public <T> Optional<T> read(Argument<T> arg) {
		
		String value = null;
		for(String vtest:values) {
			try {
				arg.check(vtest);
				value = vtest;
				break;
			} catch (ArgumentException e) {
				continue;
			}
		}
		
		if(value == null)
			return Optional.ofNullable(null);
		
		T object = null;
		try {
			object = arg.get(value);
		} catch (ExecutionException e) {}
		
		args.remove(arg);
		values.remove(value);
		
		return Optional.ofNullable(object);
	}

}
