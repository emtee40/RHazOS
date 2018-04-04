package fr.rhaz.os;

import java.io.File;

import fr.rhaz.os.chains.Element;
import fr.rhaz.os.commands.ExecutionException;
import fr.rhaz.os.commands.users.User;

public class Session {
	private Element<User> user;
	
	public Session(User user) {
		this.user = new Element<User>(user);
	}
	
	public User getUser() {
		return user.value();
	}
	
	public Element<User> getUserElement() {
		return user;
	}

	public void su(String name) throws ExecutionException{
		User user = getOS().getUser(name);
		if(user == null) throw new ExecutionException("User not found");
		setUser(new Element<User>(this.user, user));
	}
	
	public OS getOS() {
		return getUser().getOS();
	}
	
	public File getFolder() {
		return getUser().getFolder();
	}

	public void setUser(Element<User> user) {
		this.user = user;
	}
}
