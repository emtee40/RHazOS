package fr.rhaz.os;

import fr.rhaz.events.Event;
import fr.rhaz.events.EventManager;
import fr.rhaz.os.OSEvent.OSEventType;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.plugins.PluginManager;

public class OS {

	private PluginManager pluginman;
	private EventManager eventman;
	private Console console;
	private Thread thread;
	private OSEnvironment environment;
	
	public OS() {
		this(OSEnvironment.JAVA);
	}
	
	public OS(OSEnvironment env) {
		
		environment = env;
		
		thread = Thread.currentThread();
		
		console = new Console(this);
		
		eventman = new EventManager();
		
		pluginman = new PluginManager(this);
		
	}
	
	public OSEnvironment getEnvironment() {
		return environment;
	}
	
	public void defaultStart() {
		getConsole().defaultStart();
		getPluginManager().defaultStart();
	}
	
	public void started() {
		eventman.call(new OSEvent(OSEventType.STARTED));
	}
	
	public void write(String msg) {
		getConsole().getLogger().write(msg);
	}
	
	public Console getConsole() {
		return console;
	}
	
	public PluginManager getPluginManager() {
		return pluginman;
	}

	public EventManager getEventManager() {
		return eventman;
	}
	
	public void exit() {
		pluginman.exitAll();
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public void register(Command command) {
		getConsole().getCommandManager().register(command);
	}
	
	public void call(Event event) {
		getEventManager().call(event);
	}
	
	public void run(String line) {
		getConsole().process(line);
	}
	
	public static enum OSEnvironment {
		JAVA,
		ANDROID,
		OTHER;
	}
}
