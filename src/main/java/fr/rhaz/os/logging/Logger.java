package fr.rhaz.os.logging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import fr.rhaz.os.Utils;
import fr.rhaz.os.java.Function;
import fr.rhaz.os.java.Predicate;
import fr.rhaz.os.logging.def.LoggerOutput;

public class Logger {
	
	private List<Output<?>> outputs = new ArrayList<>();
	private Predicate<String> filter;
	private Function<String, String> formatter;
	private AtomicReference<Thread> process = new AtomicReference<Thread>(null);
	
	public Logger() {}
	
	public Logger(Output<?>... outputs) {
		this.outputs = Utils.list(outputs);
	}
	
	public Logger(Collection<Output<?>> outputs) {
		this.outputs = new ArrayList<>(outputs);
	}
	
	public Logger(Logger parent, Output<?>... outputs) {
		this(outputs);
		parent.addOutput(new LoggerOutput(this));
	}
	
	public Logger(Logger parent, Collection<Output<?>> outputs) {
		this(outputs);
		parent.addOutput(new LoggerOutput(this));
	}
	
	public void setOverridingProcess(Thread process) {
		this.process.set(process);
	}
	
	public Thread getOverridingProcess() {
		return process.get();
	}
	
	public void resetOverridingProcess() {
		this.process.set(null);
	}
	
	public boolean checkOverridingProcess() {
		Thread t = Thread.currentThread();
		
		if(getOverridingProcess() == null) return true;
		
		if(getOverridingProcess() != t) return false;
		
		if(getOverridingProcess().isInterrupted())
			resetOverridingProcess();
		
		return true;
	}
	
	public void write(String msg) {
			
		if(!checkOverridingProcess())
			return;
		
		if(filter(msg)) return;
			
		msg = format(msg);
		
		for(Output<?> out:outputs)
			out.write(msg);
	}
	
	public List<Output<?>> getOutputs(){
		return outputs;
	}
	
	public void addOutput(Output<?> output) {
		outputs.add(output);
	}
	
	public void setFormat(Function<String, String> formatter) {
		this.formatter = formatter;
	}
	
	public String format(String msg) {
		if(formatter == null) return msg;
		
		return formatter.apply(msg);
	}
	
	public boolean filter(String msg) {
		if(filter == null) return false;
		
		return filter.test(msg);
	}
	
	// true = do not write
	public void setFilter(Predicate<String> filter) {
		this.filter = filter;
	}
}
