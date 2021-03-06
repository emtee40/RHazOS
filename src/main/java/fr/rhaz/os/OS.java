package fr.rhaz.os;

import java.io.File;
import java.util.HashSet;

import fr.rhaz.events.Event;
import fr.rhaz.events.EventManager;
import fr.rhaz.events.EventRunnable;
import fr.rhaz.os.OS.OSEvent.OSEventType;
import fr.rhaz.os.commands.Command;
import fr.rhaz.os.commands.CommandManager;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.users.Root;
import fr.rhaz.os.commands.users.User;
import fr.rhaz.os.plugins.PluginManager;

public class OS {

	private PluginManager pluginman;
	private EventManager eventman;
	private Console console;
	private Thread thread;
	private OS.Environment environment;
	private HashSet<User> users;
	private CommandManager cmdman;
	private File folder;
	
	public OS() {
		this(OS.Environment.JAVA);
	}
	
	public OS(OS.Environment env) {
		
		environment = env;
		
		thread = Thread.currentThread();
		
		console = new Console(this);
		
		eventman = new EventManager();
		
		pluginman = new PluginManager(this);	
		
		cmdman = new CommandManager(this);
		
		users = new HashSet<>();
		
	}
	
	public OS.Environment getEnvironment() {
		return environment;
	}
	
	public void defaultStart() {
		add(new Root(this));
		setFolder(new File("."));
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
	
	public CommandManager getCommandManager() {
		return cmdman;
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
	
	public static enum Environment {
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
	
	public User getUser(String name){
		
		for(User user:getUsers())
			if(user.getName().equals(name))
				return user;
		
		return null;
		
	}
	
	public File getFolder() {
		return folder;
	}
	
	public void setFolder(File folder) {
		this.folder = folder;
	}
	
	public File getUsersFolder() {
		return new File(folder, "users");
	}
}
