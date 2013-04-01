package soundfun.user.plugins;

import net.miginfocom.swing.MigLayout;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import soundfun.plugins.Plugin;
import soundfun.plugins.PluginInfo;
import soundfun.ui.Panel;

@PluginImplementation
public class EntryPoint implements Plugin {
	/*
	 * DO NOT INITIALIZE OR ASSIGN HERE. IT IS INVALID TO ALLOCATE ANYWHERE BUT setup()
	 * 
	 * Allocating anywhere but setup() or in a method called by setup() will
	 * result in unexpected behavior in SoundFun. This is because setup() is
	 * used to clone the plugin when multiple instances are needed.
	 */
	private Panel mPanel = null;
	private PluginInfo mPluginInfo = null;
	private DataContainer mDataContainer = null;
	
	@Override
	public PluginInfo setup() {
		MigLayout layout = new MigLayout("align center, w 90%, h 90%");
		mPanel = new Panel(layout);
		
		mPluginInfo = new PluginInfo();
		
		mDataContainer = new DataContainer();
		mDataContainer.setPanel(mPanel);
		
		// Register the plugin to receive action events.
		Behavior l = new Behavior(mPluginInfo, mDataContainer);
		mPluginInfo.registerAction(l);
		
		return mPluginInfo;
	}
}
