package fr.rhaz.os;

import java.util.function.Consumer;
import java.util.function.Function;

import fr.rhaz.events.CancellableEvent;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.permissions.PermissionException;
import fr.rhaz.os.commands.users.ConsoleUser;
import fr.rhaz.os.commands.users.Root;
import fr.rhaz.os.commands.users.User;
import fr.rhaz.os.logging.ConsoleLogger;
import fr.rhaz.os.logging.Reader;
import fr.rhaz.os.logging.def.SystemInput;
import fr.rhaz.os.logging.def.SystemOutput;

public class Console extends Thread {
	
	protected OS os;
	protected Reader reader;
	protected ConsoleLogger logger;
	protected Session session;
	
	public Console(OS os) {
		this.os = os;
	}
	
	public void defaultStart() {
		
		logger = new ConsoleLogger(this, new SystemOutput());
		reader = new Reader(new SystemInput());
		session = new Session(os, new Root());
		
		logger.setDefaultPrompt("> ");
		logger.resetPrompt();
		logger.setUserPrompt(true);
		logger.updatePrompt();
		
		logger.setFormat(
				new Function<String, String>() {
					@Override
					public String apply(String msg) {
						return ("["+Utils.date("HH:mm:ss")+"]"+" "+msg);
					}
				}
		);
		
		reader.setAction(new Consumer<String>() {
			@Override
			public void accept(String t) {
				logger.updatePrompt();
				process(t);
			}
		});
		
		reader.thread().start();
	}
	
	public ConsoleUser getUser() {
		return new ConsoleUser(this, session.getUser());
	}
	
	public ConsoleUser getUser(User user) {
		return new ConsoleUser(this, user);
	}
	
	public ConsoleUser getUser(String name) throws ExecutionException {
		return new ConsoleUser(this, getOS().getUser(name));
	}
	
	public CommandManager getCommandManager() {
		return getOS().getCommandManager();
	}
	
	public ConsoleLogger getLogger() {
		return logger;
	}
	
	public Reader getReader() {
		return reader;
	}
	
	public Session getSession() {
		return session;
	}
	
	public OS getOS() {
		return os;
	}

	public void process(String line) {
		try {
			ConsoleReadEvent e = new ConsoleReadEvent(this, line);
			getOS().call(e);
			if(!e.isCancelled())
				getCommandManager().run(getUser(), Utils.getArgs(e.getLine()), e.getLine());
		} catch (PermissionException | ArgumentException e) {
			getLogger().write(e.getMessage());
		}
	}
	
	public static class ConsoleReadEvent extends CancellableEvent{
		private String line;
		private Console console;

		public ConsoleReadEvent(Console console, String line) {
			this.console = console;
			this.line = line;
		}
		
		public Console getConsole() {
			return console;
		}
		
		public User getUser() {
			return console.getSession().getUser();
		}
		
		public String getLine() {
			return line;
		}
		
		public void setLine(String line) {
			this.line = line;
		}
	}
}
