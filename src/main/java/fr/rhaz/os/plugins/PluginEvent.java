package fr.rhaz.os.plugins;

import fr.rhaz.events.Event;

public class PluginEvent extends Event {
	
	private PluginEvent.Type type;
	private PluginDescription plugin;

	public PluginEvent(PluginDescription plugin, PluginEvent.Type type) {
		this.plugin = plugin;
		this.type = type;
	}
	
	public PluginDescription getPlugin() {
		return plugin;
	}
	
	public PluginEvent.Type getType() {
		return type;
	}
	
	public static enum Type{
		LOADED,
		ENABLED,
		DISABLED,
		UNLOADED;
	}
}
