package fr.rhaz.os.logging;

import java.util.concurrent.TimeUnit;

import fr.rhaz.os.java.Consumer;

public class Reader implements Runnable {

	private Thread thread;
	private Input<?> input;
	private Consumer<String> action;
	private long delay = 500;

	public Reader(Input<?> input) {
		this.input = input;
	}
	
	public void setAction(Consumer<String> action) {
		this.action = action;
	}

	public Thread thread() {
		this.thread = new Thread(this);
		return this.thread;
	}
	
	// Default 500 milliseconds
	// Usage: setDelay(500, TimeUnit.MILLISECOND)
	public void setDelay(int delay, TimeUnit unit) {
		this.delay = unit.toMillis(delay);
	}
	
	public Thread getThread() {
		return thread;
	}
	
	@Override
	public void run() {
		
		String line;
		while((line = getInput().read()) != null) {
			
			if(action != null)
			action.accept(line);
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				return;
			}
		}

	}
	
	public Input<?> getInput(){
		return input;
	}
	
	public void setInput(Input<?> input) {
		this.input = input;
	}
	
}
