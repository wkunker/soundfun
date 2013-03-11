package soundfun.plugins;

public class PluginContainer {
	private Plugin mPlugin;
	private PluginInfo mPluginInfo;
	
	PluginContainer() {}
	
	/*
	 * Copy constructor is provided here because multiple instances
	 * of plugins will be necessary for using the same plugin on
	 * different buttons with different states.
	 * 
	 * The instance of Plugin stored in this PluginContainer
	 * WILL STILL BE REFERENCED. Only PluginInfo and its members
	 * will truly be copied.
	 */
	PluginContainer(PluginContainer copy) {
		this.mPluginInfo = copy.mPluginInfo;
	}
	
	public Plugin getPlugin() {
		return mPlugin;
	}
	
	public void setPlugin(Plugin plugin) {
		mPlugin = plugin;
	}
	
	public PluginInfo getPluginInfo() {
		return mPluginInfo;
	}
	
	public void setPluginInfo(PluginInfo info) {
		mPluginInfo = info;
	}
}
