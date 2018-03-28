package fr.rhaz.os.logging;

import fr.rhaz.os.Console;
import fr.rhaz.os.Session;

public class ConsoleLogger extends Logger {

	private String dprompt;
	private String prompt;
	private boolean userPrompt;
	private Console console;

	public ConsoleLogger(Console console) {
		super(console.getOS());
		this.console = console;
	}
	
	public ConsoleLogger(Console console, Output<?>...outputs) {
		super(console.getOS(), outputs);
		this.console = console;
	}

	public Console getConsole() {
		return console;
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
	
	public void setUserPrompt(boolean enabled) {
		this.userPrompt = enabled;
	}
	
	public Session getSession() {
		return getConsole().getSession();
	}
	
	public void updatePrompt() {
		for(Output<?> out:getOutputs()) {
			if(!(out instanceof Promptable)) continue;
			Promptable sout = (Promptable) out;
			sout.setPrompt((userPrompt?("~"+getSession().getUser().getName()):"")+prompt);
		}
	}

}
