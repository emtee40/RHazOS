package fr.rhaz.os.commands;

public abstract class CommandExecutor {
	@SuppressWarnings("rawtypes")
	private Argument[] args;
	private String desc;

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
	
	public CommandExecutor setDescription(String desc) {
		this.desc = desc;
		return this;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String toString() {
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

	public abstract void run(CommandLine line) throws ExecutionException;

	public abstract void check(CommandLine line) throws PermissionException;
}
