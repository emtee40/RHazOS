package fr.rhaz.os.logging.def;

import fr.rhaz.os.logging.Logger;
import fr.rhaz.os.logging.Output;

// Redirect your output to another logger
// this -> other: this.addOutput(new LoggerOutput(other)) 
// or 
// other -> this: other.addOutput(new LoggerOutput(this))
public class LoggerOutput extends Output<Logger>{

	public LoggerOutput(Logger out) {
		super(out);
	}

	@Override
	public void write(String msg) {
		out.write(msg);
	}
}
