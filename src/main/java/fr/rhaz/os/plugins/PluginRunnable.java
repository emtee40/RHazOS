package fr.rhaz.os.plugins;

import java.util.ArrayList;
import java.util.Optional;

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
					while(!plugin.isEnabling()) {}
					plugin.onEnable();
					if(plugin.isDisabled()) break enable;
					plugin.setEnabled();
				}
				
				while(plugin.isEnabled()) {
					executeTask();
				}
				
				plugin.onDisable();
				plugin.setDisabled();
				
				plugin.onUnload();
				plugin.setUnloaded();
				
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		public PluginManager getPluginManager() {
			return pluginmanager;
		}
		
		public Plugin getPlugin() {
			return plugin;
		}
		
		public void executeTask() {
			Optional<Runnable> task;
			if((task = tasks.stream().findFirst()).isPresent())
				task.get().run();
		}

		public void setThread(Thread thread) {
			this.thread = thread;
		}
		
		public Thread getThread() {
			return thread;
		}
	
}
