package fr.rhaz.os;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarFile;

import utils.stream.Unthrow;

public class PluginManager {
	private OS os;
	private ArrayList<PluginRunnable> plugins;
	private File folder;
	
	public PluginManager(OS os) {
		this.os = os;
		this.plugins = new ArrayList<>();
		this.folder = new File("plugins");
		if(!folder.exists()) folder.mkdir();
	}
	
	public void start() {
		loadAll();
		Unthrow.wrapProc(() -> Thread.sleep(1000));
		enableAll();
	}
	
	public void loadAll() {
		File[] files = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(".jar");
			}
		});
		
		ClassLoader parent = getClass().getClassLoader();
		for(File file:files) {
			try {
				
				JarFile jar = new JarFile(file);
				String main = jar.getManifest().getMainAttributes().getValue("Plugin-Class");
				jar.close();
				
				if(main == null) continue;
				
				URLClassLoader loader = new URLClassLoader(new URL[] {file.toURI().toURL()}, parent);
				Class<? extends Plugin> pluginclass = Class.forName(main, true, loader).asSubclass(Plugin.class);
				load(pluginclass);
				parent = loader;
				
			} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void load(Class<? extends Plugin> pluginclass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		PluginRunnable pr = new PluginRunnable(this, pluginclass);
		plugins.add(pr);
		new Thread(pr).start();
	}
	
	public void enableAll() {
		plugins.forEach((p) -> {
			p.getPlugin().setEnabling();
		});
	}

	public OS getOS() {
		return os;
	}
}
