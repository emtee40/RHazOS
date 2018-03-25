package fr.rhaz.os;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.rhaz.events.CancellableEvent;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.arguments.ArgumentException;
import fr.rhaz.os.commands.permissions.PermissionException;
import fr.rhaz.os.commands.users.User;
import fr.rhaz.os.java.Consumer;
import fr.rhaz.os.java.Function;
import fr.rhaz.os.logging.Input;
import fr.rhaz.os.logging.Logger;
import fr.rhaz.os.logging.Output;
import fr.rhaz.os.logging.Reader;
import fr.rhaz.os.logging.def.SystemInput;
import fr.rhaz.os.logging.def.SystemOutput;

public class Console extends Thread {
	
	private Reader reader;
	private Logger logger;
	private OS os;
	private CommandManager cmdman;
	private Output<?> output;

	public Console(OS os) {
		this(os, new SystemInput(), new SystemOutput("> "));
	}
	
	public Console(OS os, Input<?> input, Output<?> output) {
		
		this.os = os;
		
		this.output = output;
		
		this.cmdman = new CommandManager(os);
		
		this.logger = new Logger(output);
		
		this.reader = new Reader(input);
		
	}
	
	public Output<?> getOutput(){
		return output;
	}
	
	public void defaultStart() {
		
		logger.setFormat(
				new Function<String, String>() {
					@Override
					public String apply(String msg) {
						return ("["+date("HH:mm:ss")+"]"+" "+msg);
					}
				}
		);
		
		reader.setAction(new Consumer<String>() {
			@Override
			public void accept(String t) {
				process(t);
			}
		});
		
		reader.thread().start();
	}
	
	public String date(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	public CommandManager getCommandManager() {
		return cmdman;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public Reader getReader() {
		return reader;
	}
	
	public OS getOS() {
		return os;
	}
	
	public static String[] getArgs(String line) {
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
		List<String> list = new ArrayList<String>();
		while (m.find()) {
			String msg = m.group(1);
			if(msg.startsWith("\"") && msg.endsWith("\""))
				msg = msg.substring(1, msg.length()-1);
			list.add(msg);
		}
		return list.toArray(new String[list.size()]);
	}

	public void process(String line) {
		try {
			ConsoleReadEvent e = new ConsoleReadEvent(this, line);
			getOS().call(e);
			if(!e.isCancelled())
			getCommandManager().run(getArgs(e.getLine()), e.getLine());
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
			return console.getOS().getUser();
		}
		
		public String getLine() {
			return line;
		}
		
		public void setLine(String line) {
			this.line = line;
		}
	}
}
