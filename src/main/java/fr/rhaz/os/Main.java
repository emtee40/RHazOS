package fr.rhaz.os;

public class Main {
	private static OS os;
	
	public static void main(String[] args) {
		os = new OS();
		os.defaultStart();
		os.started();
	}
}
