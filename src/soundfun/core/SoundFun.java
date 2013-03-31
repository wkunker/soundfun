package soundfun.core;

import java.util.ArrayList;
import soundfun.plugins.PluginContainer;
import soundfun.plugins.PluginManager;
import soundfun.sound.SoundManager;
import soundfun.ui.UIManager;
import soundfun.util.Log;

/*
 * Main entry point for the SoundFun application.
 */
public class SoundFun {
	public static void main(String []args) {
		try {
			Log.logMessage("Starting SoundFun...");

			UIManager mUIManager = UIManager.getSingleton();
			PluginManager mPluginManager = PluginManager.getSingleton();
			SoundManager.getSingleton(); // Initialize the SoundManager.
			
			ArrayList<PluginContainer> plugins = mPluginManager.getLoadedPlugins();
			
			/*
			 * Populate the action list, which doesn't change
			 * throughout the life of the program.
			 */
			for(PluginContainer c : plugins) {
				try {
					mUIManager.getMainWindow().getActionListPanel().addListElement(c.getPluginInfo().getTitle());
					try {
						c.setPluginInfo(c.getPlugin().setup());
					} catch(Exception e) {
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				new ButtonListLogic();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ActionListLogic actionLogic = new ActionListLogic();
			StateManager.getSingleton().setActionListLogic(actionLogic);
			
			// Initialize the SerialManager class.
			soundfun.serial.SerialManager sm =
					soundfun.serial.SerialManager.getSingleton();
			
			// Fire up serial interface.
			SerialLogic serialLogic = new SerialLogic();
			sm.addSerialInterface(serialLogic);
			sm.startSerialListener();
			
			mPluginManager.dispose();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
