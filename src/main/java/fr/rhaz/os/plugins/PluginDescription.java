package fr.rhaz.os.plugins;

import java.io.File;

public class PluginDescription {
	private String name;
	private String author;
	private String version;
	private Class<? extends Plugin> main;
	private File file;
	private String mainname;
	
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setPluginClass(Class<? extends Plugin> main) {
		this.main = main;
	}
	
	public Class<? extends Plugin> getPluginClass(){
		return main;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public File getFolder() {
		File folder = new File(getFile().getParentFile(), getName());
		if(!folder.exists()) folder.mkdirs();
		return folder;
	}
	
	public void setPluginClassName(String name) {
		this.mainname = name;
	}
	
	public String getPluginClassName() {
		return mainname;
	}
}