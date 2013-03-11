package soundfun.core;

import java.util.HashMap;

import soundfun.plugins.PluginInfo;
import soundfun.ui.UIManager;

/*
 * Central location for accessing and setting
 * states and various properties in states.
 */
public class StateManager {
	private ActionListLogic mActionListLogic = null;
	
	/*
	 * Singleton
	 */
	private static StateManager mSingleton = null;
	public static StateManager getSingleton() {
		if(mSingleton == null) {
			mSingleton = new StateManager();
		}
		
		return mSingleton;
	}
	
	public void setActionListLogic(ActionListLogic a) {
		mActionListLogic = a;
	}
	
	public ActionListLogic getActionListLogic() {
		return mActionListLogic;
	}
	
	/*
	 * Maintains the state of the action list for each selected state.
	 * This is done since the state of the action list changes as the
	 * state of the button list changes.
	 * 
	 * The key represents the currently selected button, while the
	 * PluginInfo holds the currently selected action, based
	 * on the selected button.
	 */
	private HashMap<String, PluginInfo> mCurrentStates = new HashMap<String, PluginInfo>();
	private String mSelectedButton = null;
	
	public HashMap<String, PluginInfo> getCurrentStates() {
		return mCurrentStates;
	}
	
	public void setSelectedButton(String name) {
		mSelectedButton = name;
		
		String title = getCurrentStates().get(name).getTitle();
		// Visual update.
		UIManager.getSingleton().getMainWindow().getActionListPanel().getList().setSelectedValue(title, true);
		mActionListLogic.selected(title);
	}
	
	public String getSelectedButton() {
		return mSelectedButton;
	}
	
	/*
	 * Contains a reference to every plugin used by the history of the program.
	 * String should be in the format "ButtonTitle::ActionTitle"
	 * 	ex: "Button A::Karlor's Plugin 12"
	 */
	private HashMap<String, PluginInfo> mPluginHistoryReferences = new HashMap<String, PluginInfo>();
	
	public HashMap<String, PluginInfo> getPluginHistoryReferences() {
		return mPluginHistoryReferences;
	}
}
