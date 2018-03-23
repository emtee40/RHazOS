package fr.rhaz.os.commands;

import java.util.ArrayList;
import fr.rhaz.os.OS;
import fr.rhaz.os.Utils;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.def.ExitCommand;
import fr.rhaz.os.commands.def.HelpCommand;
import fr.rhaz.os.commands.def.SuCommand;
import fr.rhaz.os.commands.def.SudoCommand;
import fr.rhaz.os.commands.def.WhoAmICommand;
import fr.rhaz.os.commands.permissions.PermissionException;
import fr.rhaz.os.commands.users.CommandSender;

public class CommandManager {
	
	private OS os;
	
	private HelpCommand help;
	private SuCommand su;
	private WhoAmICommand whoami;
	private ExitCommand exit;

	private SudoCommand sudo;

	public CommandManager(OS os) {
		this.os = os;
		this.commands = new ArrayList<>();
		
		help = new HelpCommand(this);
		register(help);
		
		su = new SuCommand(this);
		register(su);
		
		whoami = new WhoAmICommand();
		register(whoami);
		
		exit = new ExitCommand(this);
		register(exit);
		
		sudo = new SudoCommand(this);
		register(sudo);
	}
	
	public OS getOS() {
		return os;
	}
	
	private ArrayList<Command> commands;
	
	public ArrayList<Command> getCommands(){
		return commands;
	}
	
	public void register(Command command) {
		commands.add(command);
	}
	
	public void run(String[] line, String raw) throws ArgumentException, PermissionException {
		run(getOS().getUser(), line, raw);
	}
	
	public void run(final CommandSender sender, final String[] line, final String raw) throws ArgumentException, PermissionException {
	
		if(line.length == 0) return;
		
		for(final Command command:commands) {
			try {
				command.check(line[0]);
				command.check(sender);
				final String[] args = Utils.removeFirst(line);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							command.run(line[0], sender, args, raw);
						} catch (ExecutionException | PermissionException | ArgumentException e) {
							sender.write(e.getMessage());
						}
					}
				}).start();
				return;
			} catch (ArgumentException e) {}
		}
		
		throw new ArgumentException("Unknown command. Type 'help' for help.");
	}
}
