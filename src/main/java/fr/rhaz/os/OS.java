package fr.rhaz.os;

import java.util.ArrayList;
import java.util.HashSet;

import fr.rhaz.events.Event;
import fr.rhaz.events.EventManager;
import fr.rhaz.events.EventRunnable;
import fr.rhaz.os.OS.OSEvent.OSEventType;
import fr.rhaz.os.chains.Element;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.users.Root;
import fr.rhaz.os.commands.users.User;
import fr.rhaz.os.plugins.PluginManager;

public class OS {

	private PluginManager pluginman;
	private EventManager eventman;
	private Console console;
	private Thread thread;
	private OSEnvironment environment;
	private HashSet<User> users;
	private Element<User> user;
	
	public OS() {
		this(OSEnvironment.JAVA);
	}
	
	public OS(OSEnvironment env) {
		
		environment = env;
		
		thread = Thread.currentThread();
		
		console = new Console(this);
		
		eventman = new EventManager();
		
		pluginman = new PluginManager(this);
		
		users = new HashSet<User>();
		Root root = new Root(this);
		user = new Element<User>(root);
		users.add(root);
		
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
	
	public void register(EventRunnable<? extends Event> runnable) {
		getEventManager().register(runnable);
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
	
	public static class OSEvent extends Event {
		
		private OSEventType type;

		public OSEvent(OSEventType type) {
			this.type = type;
		}
		
		public OSEventType getType() {
			return type;
		}
		
		public static enum OSEventType{
			STARTED;
		}
	}
	
	public HashSet<User> getUsers(){
		return users;
	}
	
	public void add(User user) {
		users.add(user);
	}
	
	public User getUser() {
		return user.value();
	}
	
	public Element<User> getUserElement() {
		return user;
	}

	public void su(String user) throws NullPointerException{
		setUser(new Element<User>(this.user, getUser(user)));
	}
	
	public User getUser(String name) throws NullPointerException{
		
		for(User user:users)
			if(user.getName().equals(name))
				return user;
		
		throw new NullPointerException("User not found");
		
	}
	
	public void setUser(Element<User> user) {
		this.user = user;
	}
}
