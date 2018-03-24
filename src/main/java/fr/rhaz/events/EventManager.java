package fr.rhaz.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.rhaz.os.java.Consumer;

public class EventManager {

	private Map<Integer, List<EventRunnable<? extends Event>>> events;
	
	public EventManager() {
		events.put(0, new ArrayList<EventRunnable<? extends Event>>());
		events.put(1, new ArrayList<EventRunnable<? extends Event>>());
		events.put(2, new ArrayList<EventRunnable<? extends Event>>());
	}
	
	public void register(EventRunnable<? extends Event> runnable) {
		register(Priority.NORMAL, runnable);
	}
	
	public synchronized void register(Priority priority, EventRunnable<? extends Event> runnable) {
		switch(priority) {
			case FIRST:{
				events.get(0).add(runnable); break;
			}
			case NORMAL:{
				events.get(1).add(runnable); break;
			}
			case LAST:{
				events.get(2).add(runnable); break;
			}
		}
	}
	
	public synchronized void call(final Event event) {
		
		Consumer<? super EventRunnable<? extends Event>> call = new Consumer<EventRunnable<? extends Event>>() {
			@Override
			public void accept(EventRunnable<? extends Event> r) {
				
				if(!r.type().equals(event.getClass())) return;
				
				cancel:{
					if(!(event instanceof CancellableEvent)) break cancel;
					CancellableEvent c = (CancellableEvent) event;
					
					if(!c.isCancelled()) break cancel;
					if(r.ignoreCancelled()) break cancel;
					
					return;
				}
				
				r.call(event);
				
			}
		};
		
		for(EventRunnable<? extends Event> plugin:events.get(0)) call.accept(plugin);
		for(EventRunnable<? extends Event> plugin:events.get(1)) call.accept(plugin);
		for(EventRunnable<? extends Event> plugin:events.get(2)) call.accept(plugin);
	}
	
	public ArrayList<EventRunnable<? extends Event>> getAllRunnables(){
		ArrayList<EventRunnable<? extends Event>> runnables = new ArrayList<>();
		runnables.addAll(firsts);
		runnables.addAll(normals);
		runnables.addAll(lasts);
		return runnables;
	}
}
