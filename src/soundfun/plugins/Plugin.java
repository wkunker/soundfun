package soundfun.plugins;

/*
 * The starting point for making a plugin which can extend the capability of SoundFun.
 * A plugin is meant to represent an "action" in SoundFun, meaning that it manipulates
 * a sound/sample, program behavior/functionality, or even the interface capabilities.
 * The entry point of an "action" is normally through a serial event, or button push.
 * 
 * Plugins are placed in the /plugins directory of SoundFun and loaded automatically
 * when the program starts. setup() is called upon plugin load.
 * update() is called whenever a UI event occurs.
 */
public interface Plugin extends net.xeoh.plugins.base.Plugin {	
	/*
	 * Perform initial construction of the plugin.
	 * Returns an instance of PluginInfo, which is used to provide
	 * SoundFun and users with detailed information about the plugin.
	 * 
	 * This is the entry point for every instance a plugin
	 * that is spawned in SoundFun.
	 */
	public PluginInfo setup();
}
