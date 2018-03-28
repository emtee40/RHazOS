package fr.rhaz.os;

import fr.rhaz.os.chains.Element;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.users.User;

public class Session {
	private Element<User> user;
	private OS os;
	
	public Session(OS os, User user) {
		this.os = os;
		this.user = new Element<User>(user);
	}
	
	public User getUser() {
		return user.value();
	}
	
	public Element<User> getUserElement() {
		return user;
	}

	public void su(String user) throws ExecutionException{
		setUser(new Element<User>(this.user, getOS().getUser(user)));
	}
	
	private OS getOS() {
		return os;
	}

	public void setUser(Element<User> user) {
		this.user = user;
	}
}
