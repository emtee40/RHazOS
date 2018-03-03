package fr.rhaz.events;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventManager {

	private ArrayList<EventRunnable<? extends Event>> firsts;
	private ArrayList<EventRunnable<? extends Event>> normals;
	private ArrayList<EventRunnable<? extends Event>> lasts;
	
	public EventManager() {
		firsts = new ArrayList<>();
		normals = new ArrayList<>();
		lasts = new ArrayList<>();
	}
	
	public void register(EventRunnable<? extends Event> runnable) {
		register(Priority.NORMAL, runnable);
	}
	
	public void register(Priority priority, EventRunnable<? extends Event> runnable) {
		switch(priority) {
			case FIRST:{
				firsts.add(runnable); break;
			}
			case NORMAL:{
				normals.add(runnable); break;
			}
			case LAST:{
				lasts.add(runnable); break;
			}
		}
	}
	
	public void call(Event event) {
		
		Consumer<? super EventRunnable<? extends Event>> call = (r) -> {
			
			if(!r.type().equals(event.getClass())) return;
			
			cancel:{
				if(!(event instanceof CancellableEvent)) break cancel;
				CancellableEvent c = (CancellableEvent) event;
				
				if(!c.isCancelled()) break cancel;
				System.out.println("cancelled!");
				if(r.ignoreCancelled()) break cancel;
				
				return;
			}
			
			r.call(event);
			
		};
		
		firsts.forEach(call);
		normals.forEach(call);
		lasts.forEach(call);
	}
	
	public ArrayList<EventRunnable<? extends Event>> getAllRunnables(){
		ArrayList<EventRunnable<? extends Event>> runnables = new ArrayList<>();
		runnables.addAll(firsts);
		runnables.addAll(normals);
		runnables.addAll(lasts);
		return runnables;
	}
}
