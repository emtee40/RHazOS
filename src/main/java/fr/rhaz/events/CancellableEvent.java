package fr.rhaz.events;

public class CancellableEvent extends Event {
	private boolean cancelled;
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
}