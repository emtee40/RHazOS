package fr.rhaz.os.plugins;

import fr.rhaz.os.OS;

public abstract class Plugin {
	
	private PluginDescription desc;
	private PluginRunnable runnable;
	private Status status = Status.UNLOADED;
	
	public Plugin() {
		this.desc = new PluginDescription();
	}
	
	public void exit() {
		Thread.currentThread().interrupt();
	}
	
	public OS getOS() {
		return runnable.getPluginManager().getOS();
	}
	
	public abstract void onLoad();
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract void onUnload();
	public abstract void onExit();
	
	public PluginDescription getDescription() {
		return desc;
	}
	
	public boolean isEnabled() {
		return status.equals(Status.ENABLED);
	}
	
	public void setEnabled() {
		status = Status.ENABLED;
	}
	
	public boolean isEnabling() {
		return status.equals(Status.ENABLING);
	}
	
	public void setEnabling() {
		status = Status.ENABLING;
	}
	
	public boolean isLoaded() {
		return status.equals(Status.LOADED);
	}
	
	public void setLoaded() {
		status = Status.LOADED;
	}
	
	public boolean isUnloaded() {
		return status.equals(Status.UNLOADED);
	}
	
	public void setUnloaded() {
		status = Status.UNLOADED;
	}
	
	public boolean isDisabled() {
		return status.equals(Status.DISABLED);
	}
	
	public void setDisabled() {
		status = Status.DISABLED;
	}
	
	public static enum Status{
		UNLOADED,
		LOADED,
		ENABLING,
		ENABLED,
		DISABLED;
	}

	public void setDescription(PluginDescription pluginDesc) {
		this.desc = pluginDesc;
	}

	public void setRunnable(PluginRunnable pluginRunnable) {
		this.runnable = pluginRunnable;
	}
	
	public PluginRunnable getRunnable() {
		return runnable;
	}
	
}
