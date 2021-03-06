package fr.rhaz.os.logging.def;

import java.io.PrintStream;

import fr.rhaz.os.logging.Output;
import fr.rhaz.os.logging.Promptable;

public class SystemOutput extends Output<PrintStream> implements Promptable {

	private String prompt = "";

	public SystemOutput() {
		super(System.out);
		this.prompt = "";
	}
	
	public SystemOutput(String prompt) {
		super(System.out);
		this.prompt = prompt;
	}

	public void clearPrompt() {
		for(int i=0; i <= prompt.length(); i++)
			out.print("\b");
	}
	
	@Override
	public void setPrompt(String prompt) {
		clearPrompt();
		this.prompt = prompt;
		out.print(prompt);
	}
	
	@Override
	public void write(String msg) {
		clearPrompt();
		out.println(msg);
		out.print(prompt);
	}

}
