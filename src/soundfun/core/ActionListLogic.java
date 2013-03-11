package soundfun.core;

import java.util.ArrayList;

import soundfun.plugins.PluginContainer;
import soundfun.plugins.PluginManager;
import soundfun.ui.UIManager;

/*
 * Controls logic for the action list (selection of action plugins).
 * 
 * This visually modifies the action list as well as the action details.
 */
public class ActionListLogic implements soundfun.ui.ListListener {
	ActionListLogic() {
		UIManager.getSingleton().getMainWindow().getActionListPanel().addListListener(this);
		
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
		 * Retrieve all plugins which that have been loaded into memory.
		 */
		ArrayList<PluginContainer> plugins = null;
		try {
			plugins = PluginManager.getSingleton().getLoadedPlugins();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Iterate through all available plugins, and update ActionDetailsPanel
		 * object to reflect the plugin that has been selected.
		 */
		for(PluginContainer c : plugins) {
			try {
				if(c.getPluginInfo().getTitle().contentEquals(actionName)) {
					UIManager.getSingleton().getMainWindow().setActionDetailsPanel(
						c.getPluginInfo().getPanel());
					
					// "Copies" the plugin to this state.
					//PluginContainer pc = o.getActionListValue().getPluginContainer();
					//pc.setPluginInfo(pc.getPlugin().setup());
					//as.setPluginContainer(pc);
					
					// Push the new StateObject into the state list.
					StateManager.getSingleton().getCurrentStates().put(StateManager.getSingleton().getSelectedButton(), c.getPluginInfo()); // Finalize.
					
					
					//StateManager.getSingleton().setSelectedButtonStateObject(o);
					
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
