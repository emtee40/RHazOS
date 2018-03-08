package fr.rhaz.os.commands;

public abstract class CommandExecutor {
	@SuppressWarnings("rawtypes")
	private Argument[] args;

	@SafeVarargs
	public CommandExecutor(Argument<?>... args) {
		this.args = args;
	}
	
	public Argument<?>[] getArguments() {
		return args;
	}
	
	public void check(String[] line) throws ArgumentException {
		for (int i = 0; i < args.length && i < line.length; i++) {
			String value = line[i];
			Argument<?> arg = args[i];
			arg.check(value);
		};
	}

	public abstract void run(CommandLine line) throws ExecutionException;

	public abstract void check(CommandSender sender) throws PermissionException;
}
