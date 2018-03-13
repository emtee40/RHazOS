package fr.rhaz.os.plugins;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.jar.JarFile;

import fr.rhaz.os.OS;
import fr.rhaz.os.Unthrow;

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
	
	public PluginManager(OS os, File folder) {
		this.os = os;
		this.plugins = new ArrayList<>();
		this.folder = folder;
		if(!folder.exists()) folder.mkdir();
	}
	
	public void defaultStart() {
		
		BiConsumer<PluginDescription, String>
		loader = (desc, main) -> injectClass(desc, main);
				
		loadAll(loader);
		
		Unthrow.wrapProc(() -> Thread.sleep(1000));
		
		enableAll();
	}
	
	public void injectClass(PluginDescription desc, String main){
		try {
			
			URLClassLoader urlloader = new URLClassLoader(new URL[] {desc.getFile().toURI().toURL()}, getClass().getClassLoader());
			Class<? extends Plugin> pluginclass = Class.forName(main, true, urlloader).asSubclass(Plugin.class);
			desc.setPluginClass(pluginclass);
			
		} catch (ClassNotFoundException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadAll(BiConsumer<PluginDescription, String> loader) {
		File[] files = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(".jar");
			}
		});
		
		for(File file:files) {
			try {
				
				JarFile jar = new JarFile(file);
				
				String main = jar.getManifest().getMainAttributes().getValue("Plugin-Class");
				String name = jar.getManifest().getMainAttributes().getValue("Plugin-Name");
				String author = jar.getManifest().getMainAttributes().getValue("Plugin-Author");
				String version = jar.getManifest().getMainAttributes().getValue("Plugin-Version");
				
				jar.close();
				
				PluginDescription desc = new PluginDescription();
				
				if(nullOrEmpty(main))
					continue;
				
				if(!nullOrEmpty(name))
					desc.setName(name);
				
				if(!nullOrEmpty(author))
					desc.setAuthor(author);
				
				if(!nullOrEmpty(version))
					desc.setVersion(version);
				
				desc.setFile(file);
				
				loader.accept(desc, main);
				
				load(desc);
				
			} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void load(PluginDescription desc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		PluginRunnable pr = new PluginRunnable(this, desc);
		plugins.add(pr);
		Thread thread = new Thread(pr);
		pr.setThread(thread);
		thread.start();
	}
	
	public void enableAll() {
		plugins.forEach((p) -> {
			p.getPlugin().setEnabling();
		});
	}
	
	public Plugin getPlugin(String name) {
		ArrayList<PluginRunnable> list = new ArrayList<>(plugins);
		list.removeIf((runnable) -> {
			return !runnable.getPlugin().getDescription().getName().equalsIgnoreCase(name);
		});
		return list.get(0).getPlugin();
	}
	
	public ArrayList<PluginRunnable> getPlugins(){
		return plugins;
	}
	
	public File getFolder() {
		return folder;
	}

	public OS getOS() {
		return os;
	}

	public void exitAll() {
		plugins.forEach((p) -> {
			p.getThread().interrupt();
		});
	}
	
	public boolean nullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
}
