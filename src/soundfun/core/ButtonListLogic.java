package soundfun.core;

import soundfun.ui.UIManager;

/*
 * Controls logic for the action list (selection of action plugins).
 * 
 * This visually modifies the action list as well as the action details.
 * 
 * Button listbox titles names characters not permitted: "::"
 */
public class ButtonListLogic implements soundfun.ui.ListListener {
	UIManager mUIManager = UIManager.getSingleton();
	
	private void addElement(String name) {
		mUIManager.getMainWindow().getButtonListPanel().addListElement(name);
		StateManager.getSingleton().getCurrentStates().put(name, null);
	}
	
	ButtonListLogic() throws CloneNotSupportedException {
		/*
		 * Populate the button list. This is a placeholder, and it will eventually
		 * be defined by a library or plugin which will read the device model.
		 */		
		addElement("Button A");
		addElement("Button B");
		addElement("Button C");
		
		mUIManager.getMainWindow().getButtonListPanel().addListListener(this);
	}
	
	/*
	 * Used by ListListener to tell the main logic when
	 * a new action has been chosen.
	 */
	@Override
	public void selected(String buttonName) {
		StateManager.getSingleton().setSelectedButton(buttonName);
		
		/*
		 * Update the action list to the correct state, since it's dependent
		 * on the state of the button list.
		 */
		
		
		
		/*
		 * Retrieve all plugins which that have been loaded into memory.
		 */
		
		/*
		ArrayList<PluginContainer> plugins = null;
		try {
			plugins = PluginManager.getSingleton().getLoadedPlugins();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		/*
		 * Iterate through all available plugins, and update ActionDetailsPanel
		 * object to reflect the plugin that has been selected.
		 */
		/*
		for(PluginContainer c : plugins) {
			try {
				if(c.getPluginInfo().getTitle().contentEquals(buttonName)) {
					UIManager.getSingleton().getMainWindow().setActionDetailsPanel(
							c.getPluginInfo().getPanel());
					
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
}
