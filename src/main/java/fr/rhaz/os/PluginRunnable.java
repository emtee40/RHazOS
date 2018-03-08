package fr.rhaz.os;

import java.util.ArrayList;
import java.util.Optional;

public class PluginRunnable implements Runnable {

		private Class<? extends Plugin> pluginclass;
		private PluginManager pluginmanager;
		private Plugin plugin = null;
		private ArrayList<Runnable> tasks;

		public PluginRunnable(PluginManager pluginmanager, Class<? extends Plugin> pluginclass) {
			this.pluginmanager = pluginmanager;
			this.pluginclass = pluginclass;
			this.tasks = new ArrayList<>();
		}

		@Override
		public void run() {
			try {
				
				this.plugin = pluginclass.newInstance();
				
				Runtime.getRuntime().addShutdownHook(
					new Thread(() -> {
						plugin.onExit();
					})
				);
				
				plugin.load(this);
				plugin.onLoad();
				plugin.setEnabling();
				
				enable:{
					plugin.onEnable();
					if(plugin.isDisabled()) break enable;
					plugin.setEnabled();
				}
				
				while(plugin.isEnabled()) {
					executeTask();
				}
				
				plugin.onDisable();
				plugin.setLoaded();
				
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
	
}
