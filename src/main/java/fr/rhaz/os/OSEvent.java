package fr.rhaz.os;

import fr.rhaz.events.Event;

public class OSEvent extends Event {
	
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
