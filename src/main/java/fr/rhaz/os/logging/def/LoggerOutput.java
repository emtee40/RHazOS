package fr.rhaz.os.logging.def;

import fr.rhaz.os.logging.Logger;
import fr.rhaz.os.logging.Output;

// Redirect your output to another logger
// this -> other: this.addOutput(new LoggerOutput(other)) 
// or 
// other -> this: other.addOutput(new LoggerOutput(this))
public class LoggerOutput extends Output<Logger>{

	private boolean format = true;

	public LoggerOutput(Logger out) {
		this(out, true);
	}
	
	public LoggerOutput(Logger out, boolean format) {
		super(out);
		this.format = format;
	}

	@Override
	public void write(String msg) {
		if(format) out.write(msg);
		else out.writeWithoutFormat(msg);
	}
}
