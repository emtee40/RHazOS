package fr.rhaz.os.plugins;

import fr.rhaz.events.Event;

public class PluginEvent extends Event {
	
	private PluginEventType type;
	private PluginDescription plugin;

	public PluginEvent(PluginDescription plugin, PluginEventType type) {
		this.plugin = plugin;
		this.type = type;
	}
	
	public PluginDescription getPlugin() {
		return plugin;
	}
	
	public PluginEventType getType() {
		return type;
	}
	
	public static enum PluginEventType{
		LOADED,
		ENABLED,
		DISABLED,
		UNLOADED;
	}
}
