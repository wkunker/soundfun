package soundfun.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import soundfun.plugins.PluginContainer;
import soundfun.plugins.PluginInfo;
import soundfun.plugins.PluginManager;
import soundfun.ui.UIManager;

/*
 * Controls logic for the action list (selection of action plugins).
 * 
 * This visually modifies the action list as well as the action details.
 */
public class ActionListLogic implements soundfun.ui.ListListener {
	ArrayList<PluginContainer> mPlugins = null;
	
	ActionListLogic() {
		UIManager.getSingleton().getMainWindow().getActionListPanel().addListListener(this);
		
		/*
		 * Retrieve all plugins which that have been loaded into memory.
		 */
		
		try {
			mPlugins = PluginManager.getSingleton().getLoadedPlugins();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Default everything to the first available.
		 */
		for(PluginContainer c : mPlugins) {
			for(String key : StateManager.getSingleton().getCurrentStates().keySet()) {
				StateManager.getSingleton().getCurrentStates().put(key, c.getPluginInfo());
			}
			break;
		}
		
		UIManager.getSingleton().getMainWindow().getActionListPanel().getList().setEnabled(false);
		
		// Force a selection to occur so the action that's selected by default is loaded into the action details panel.
		//this.selected(UIManager.getSingleton().getMainWindow().getActionListPanel().getList().getSelectedValue());
	}
	
	/*
	 * Used by ActionListListener to tell the main logic when
	 * a new action has been chosen.
	 */
	@Override
	public void selected(String actionName) {
		
		
		/*
		 * Iterate through all available plugins, and update ActionDetailsPanel
		 * object to reflect the plugin that has been selected.
		 */
		for(PluginContainer c : mPlugins) {
			try {
				if(c.getPluginInfo().getTitle().contentEquals(actionName)) {
					// Search to see if the needed plugin exists already in the history.
					HashMap<String, PluginInfo> hist = StateManager.getSingleton().getPluginHistoryReferences();
					for (Map.Entry<String, PluginInfo> entry : hist.entrySet()) {
					    if(entry.getKey().equals(StateManager.getSingleton().getSelectedButton() + "::" + actionName)) {
					    	/*
					    	 * Found the plugin in the history already. Reload it as it currently exists.
					    	 */
					    	
					    	// Update the existing plugin into the StateManager.
					    	StateManager.getSingleton().getCurrentStates().put(StateManager.getSingleton().getSelectedButton(), entry.getValue());
					    	
					    	// Tell the plugin what button is calling it.
					    	c.getPluginInfo().setLastAssignedButton(StateManager.getSingleton().getSelectedButton());
							
					    	// Visually update the existing plugin into the action details panel.
					    	UIManager.getSingleton().getMainWindow().setActionDetailsPanel(
									entry.getValue().getPanel());
					    	
					    	return;
					    }
					}
					
					/*
					 * Couldn't find the plugin in the history already. Clone a new instance.
					 */
					PluginInfo clone = c.getPlugin().setup();
					StateManager.getSingleton().getCurrentStates().put(StateManager.getSingleton().getSelectedButton(), clone);
					UIManager.getSingleton().getMainWindow().setActionDetailsPanel(
					clone.getPanel());
					hist.put(StateManager.getSingleton().getSelectedButton() + "::" + actionName, clone);
					soundfun.util.Log.logDebugMessage(this, "Adding history... " + StateManager.getSingleton().getSelectedButton() + "::" + actionName);
					
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
