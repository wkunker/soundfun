package soundfun.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import net.xeoh.plugins.base.util.PluginManagerUtil; // JSPF

/*
 * Central class for handling plugins.
 */
public class PluginManager {
	// Singleton instance.
	private static PluginManager mSingleton = null;
	
	// JSPF handles the loading of SoundFun plugins.
	net.xeoh.plugins.base.PluginManager mJSPFPluginManager;
	
	// All plugins which have been loaded into SoundFun.
	ArrayList<PluginContainer> mLoadedPlugins = null;
	
	public void dispose() {
		mJSPFPluginManager.shutdown();
	}

	/*
	 * Constructor.
	 * Private accessor is part of Singleton pattern.
	 */
	private PluginManager() throws Exception {
		// Initialize JSPF plugin manager object.
		mJSPFPluginManager = net.xeoh.plugins.base.impl.PluginManagerFactory.createPluginManager();
		
		// Retrieves all plugins located in the plugins directory of the program's root directory.
		mJSPFPluginManager.addPluginsFrom(new File("plugins/").toURI());
		
		// Initialize the JSPF utility class to load multiple plugins.
		PluginManagerUtil pmu = new PluginManagerUtil(mJSPFPluginManager);
		
		// Load all of the plugins into (I'm guessing) memory.
		// TODO: Investigate exactly how this works in the JSPF library.
		Collection<Plugin> plugins = pmu.getPlugins(Plugin.class);
		
		// Initialize the collection 
		mLoadedPlugins = new ArrayList<PluginContainer>();
		
		// Call setup() on all the plugins. setup() is the correct time to
		// allocate memory and setup the GUI layout and hooks, so expect
		// potentially long call times.
		for(Plugin p : plugins) {
			// Pack the plugin info and the plugin itself into a container.
			PluginContainer c = new PluginContainer();
			c.setPlugin(p);
			c.setPluginInfo(p.setup()); // Setup all plugins.
			mLoadedPlugins.add(c);
		}
	}
	
	/*
	 * Returns the only possible instance of PluginManager.
	 */
	public static PluginManager getSingleton() throws Exception {
		if(mSingleton == null)
			mSingleton = new PluginManager();
		
		return mSingleton;
	}
	
	/*
	 * Returns a collection of PluginContainer instances.
	 */
	public ArrayList<PluginContainer> getLoadedPlugins() {
		return mLoadedPlugins;
	}
	
	/*
	 * Retrieve the plugin by it's name, otherwise an exception is thrown.
	 */
	public Plugin getPlugin(String name) throws Exception {
		for(PluginContainer p : mLoadedPlugins) {
			if(p.getPluginInfo().getTitle().contentEquals(name))
				return p.getPlugin();
		}
		
		throw new Exception("Plugin.getPlugin(String name) was unable to find requested plugin " + name);
	}
}
