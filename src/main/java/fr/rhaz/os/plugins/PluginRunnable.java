package fr.rhaz.os.plugins;

import java.util.ArrayList;
import java.util.Optional;

import fr.rhaz.events.Event;
import fr.rhaz.os.plugins.PluginEvent.PluginEventType;

public class PluginRunnable implements Runnable {

		private PluginManager pluginmanager;
		private Plugin plugin = null;
		private ArrayList<Runnable> tasks;
		private Thread thread;
		private PluginDescription plugindesc;

		public PluginRunnable(PluginManager pluginmanager, PluginDescription desc) {
			this.pluginmanager = pluginmanager;
			this.plugindesc = desc;
			this.tasks = new ArrayList<>();
		}
		
		public void doNothing() {}

		@Override
		public void run() {
			try {
				
				plugin = plugindesc.getPluginClass().newInstance();
				
				plugin.setRunnable(this);
				plugin.setDescription(plugindesc);
				
				Runtime.getRuntime().addShutdownHook(
					new Thread(new Runnable() {
						@Override
						public void run() {
							plugin.onExit();
						}
					})
				);
				
				plugin.onLoad();
				plugin.setLoaded();
				
				enable:{
					
					while(!plugin.isEnabling())
						Thread.sleep(100);
					
					plugin.onEnable();
					if(plugin.isDisabled()) break enable;
					plugin.setEnabled();
					
					while(plugin.isEnabled()) {
						executeTask();
						Thread.sleep(100);
					}
				}
				
				plugin.onDisable();
				plugin.setDisabled();
				
				plugin.onUnload();
				plugin.setUnloaded();
				
			} catch (InstantiationException | IllegalAccessException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void enable() {
			plugin.setEnabling();
			Event e = new PluginEvent(plugin.getDescription(), PluginEventType.ENABLED);
			getPluginManager().getOS().getEventManager().call(e);
		}
		
		public PluginManager getPluginManager() {
			return pluginmanager;
		}
		
		public Plugin getPlugin() {
			return plugin;
		}
		
		public void executeTask() {
			if(tasks.size() == 0) return;
			Runnable task = tasks.get(0);
			tasks.remove(0);
			task.run();
		}
		
		public void addTask(Runnable task) {
			tasks.add(task);
		}

		public void setThread(Thread thread) {
			this.thread = thread;
		}
		
		public Thread getThread() {
			return thread;
		}
	
}
