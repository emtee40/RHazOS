package fr.rhaz.os.commands;

import com.google.common.base.Strings;

import fr.rhaz.os.commands.arguments.Argument;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.permissions.PermissionException;

public abstract class CommandExecutor {
	@SuppressWarnings("rawtypes")
	private Argument[] args;
	private String desc;
	private String hint;

	@SafeVarargs
	public CommandExecutor(String desc, Argument<?>... args) {
		this.args = args;
		this.desc = desc;
	}
	
	@SafeVarargs
	public CommandExecutor(Argument<?>... args) {
		this.args = args;
		this.desc = "";
	}
	
	public Argument<?>[] getArguments() {
		return args;
	}
	
	public CommandExecutor setHint(String hint) {
		this.hint = hint;
		return this;
	}
	
	public String getHint() {
		return hint;
	}
	
	public CommandExecutor setDescription(String desc) {
		this.desc = desc;
		return this;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String toString() {
		
		if(!Strings.isNullOrEmpty(hint))
			return hint;
		
		String result = "";
		for(Argument<?> arg:args)
			result += arg + " ";
		return result.trim();
	}
	
	public void check(String[] line) throws ArgumentException {
		
		if(line.length < args.length) 
			throw new ArgumentException();
		
		for (int i = 0; i < args.length; i++) {
			String value = line[i];
			Argument<?> arg = args[i];
			arg.check(value);
		};
	}

	public abstract void run(CommandLine context) throws ExecutionException, PermissionException, ArgumentException;

	public void check(CommandLine context) throws PermissionException{};
}
