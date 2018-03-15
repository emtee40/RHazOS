package fr.rhaz.os;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.rhaz.os.commands.ArgumentException;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.PermissionException;
import fr.rhaz.os.logging.ConsoleInput;
import fr.rhaz.os.logging.ConsoleOutput;
import fr.rhaz.os.logging.Input;
import fr.rhaz.os.logging.Logger;
import fr.rhaz.os.logging.Output;

public class Console extends Thread {
	
	private Reader reader;
	private Logger logger;
	private Input<?> input;
	private OS os;
	private CommandManager cmdman;

	public Console(OS os) {
		this(os, new ConsoleInput(), new ConsoleOutput());
	}
	
	public Console(OS os, Input<?> input, Output<?> output) {
		
		this.os = os;
		
		this.cmdman = new CommandManager();
		
		this.input = input;
		
		this.logger = new Logger("RHasOS", output);
		
		this.reader = new Reader();
		
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
	
	public Input<?> getInput(){
		return input;
	}
	
	public void setInput(Input<?> input) {
		this.input = input;
	}
	
	public OS getOS() {
		return os;
	}
	
	public class Reader implements Runnable{

		private Thread thread;

		public Thread thread() {
			this.thread = new Thread(this);
			return this.thread;
		}
		
		@Override
		public void run() {
			String line;
			while((line = getInput().read()) != null) {
				process(line);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					return;
				}
			}

		}
		
	}

	public void process(String line) {
		try {
			Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
			List<String> list = new ArrayList<String>();
			while (m.find()) {
				String msg = m.group(1);
				if(msg.startsWith("\"") && msg.endsWith("\""))
					msg = msg.substring(1, msg.length()-1);
				list.add(msg);
			}
			getCommandManager().run(list.toArray(new String[list.size()]));
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (PermissionException e) {
			getLogger().write(e.getMessage());
		} catch (ArgumentException e) {
			getLogger().write(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
