package fr.rhaz.os.logging;

import java.io.PrintStream;

public class ConsoleOutput extends Output<PrintStream>{

	public ConsoleOutput() {
		super(System.out);
	}

	@Override
	public void write(String msg) {
		out.println(msg);
	}

}
