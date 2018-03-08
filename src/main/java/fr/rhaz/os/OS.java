package fr.rhaz.os;

import fr.rhaz.events.EventManager;
import fr.rhaz.os.OSEvent.OSEventType;

public class OS {
	
	private PluginManager pluginman;
	private EventManager eventman;
	private Console console;
	
	public OS() {
		
		console = new Console(this);
		
		eventman = new EventManager();
		
		pluginman = new PluginManager(this);
		getPluginManager().start();
		
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
	
}
