package fr.rhaz.os.logging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import fr.rhaz.os.OS;
import fr.rhaz.os.Utils;
import fr.rhaz.os.chains.Tree;
import fr.rhaz.os.java.Function;
import fr.rhaz.os.java.Predicate;
import fr.rhaz.os.logging.def.LoggerOutput;

public class Logger {
	
	private List<Output<?>> outputs = new ArrayList<>();
	private Predicate<String> filter;
	private Function<String, String> formatter;
	private Tree<Thread> process = null;
	private String dprompt;
	private String prompt;
	private OS os;
	
	public Logger(OS os) {
		this.os = os;
	}
	
	public Logger(OS os, Output<?>... outputs) {
		this(os);
		this.outputs = Utils.list(outputs);
	}
	
	public Logger(OS os, Collection<Output<?>> outputs) {
		this(os);
		this.outputs = new ArrayList<>(outputs);
	}
	
	public Logger(OS os, Logger parent, Output<?>... outputs) {
		this(os, outputs);
		parent.addOutput(new LoggerOutput(this));
	}
	
	public Logger(OS os, Logger parent, Collection<Output<?>> outputs) {
		this(os, outputs);
		parent.addOutput(new LoggerOutput(this));
	}
	
	public OS getOS() {
		return os;
	}
	
	public void setOverridingProcess(Thread process) {
		this.process = new Tree<Thread>(process);
	}
	
	public Tree<Thread> getOverridingTree() {
		return process;
	}
	
	public void addOverridingProcess(Thread process) {
		if(process == null) this.process = new Tree<Thread>(process);
		else this.process.getChildren().add(process);
	}
	
	public void resetOverridingProcess() {
		this.process = null;
	}
	
	public boolean checkOverridingProcess() {
		Thread t = Thread.currentThread();
		
		if(getOverridingTree() == null) return true;
		
		if(!getOverridingTree().contains(t)) return false;
		
		if(getOverridingTree().getParent().isInterrupted())
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
	
	public String getDefaultPrompt() {
		return dprompt;
	}
	
	public void setDefaultPrompt(String dprompt) {
		this.dprompt = dprompt;
	}
	
	public void resetPrompt() {
		prompt = dprompt;
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public void updatePrompt() {
		for(Output<?> out:outputs) {
			if(!(out instanceof Promptable)) continue;
			Promptable sout = (Promptable) out;
			sout.setPrompt(prompt);
		}
	}
}
