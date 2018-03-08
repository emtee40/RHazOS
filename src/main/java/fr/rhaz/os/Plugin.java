package fr.rhaz.os;

public abstract class Plugin {
	
	private Description desc;
	private PluginRunnable runnable;
	private Status status = Status.UNLOADED;
	
	public Plugin() {
		this.desc = new Description();
	}
	
	public void load(PluginRunnable runnable) {
		this.runnable = runnable;
	}
	
	public void exit() {
		Thread.currentThread().interrupt();
	}
	
	public OS getOS() {
		return runnable.getPluginManager().getOS();
	}
	
	public abstract void onLoad();
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract void onUnload();
	public abstract void onExit();
	
	public Description getDescription() {
		return desc;
	}
	
	public static class Description {
		private String name;
		private String author;
		private int version;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
	}
	
	public boolean isEnabled() {
		return status .equals(Status.ENABLED);
	}
	
	public void setEnabled() {
		status = Status.ENABLED;
	}
	
	public boolean isEnabling() {
		return status.equals(Status.ENABLING);
	}
	
	public void setEnabling() {
		status = Status.ENABLING;
	}
	
	public boolean isLoaded() {
		return status.equals(Status.LOADED);
	}
	
	public void setLoaded() {
		status = Status.LOADED;
	}
	
	public boolean isUnloaded() {
		return status.equals(Status.UNLOADED);
	}
	
	public void setUnloaded() {
		status = Status.UNLOADED;
	}
	
	public boolean isDisabled() {
		return status.equals(Status.DISABLED);
	}
	
	public void setDisabled() {
		status = Status.DISABLED;
	}
	
	public static enum Status{
		UNLOADED,
		LOADED,
		ENABLING,
		ENABLED,
		DISABLED;
	}
	
}
