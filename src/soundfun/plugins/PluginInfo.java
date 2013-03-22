package soundfun.plugins;

import java.util.ArrayList;

import soundfun.ui.Panel;

/*
 * Identification for each plugin. An instance of PluginInfo is
 * returned from the setup() method in the plugin to SoundFun.
 */
public class PluginInfo {
	private String mLastAssignedButton = null;
	private ArrayList<Action> mActions = new ArrayList<Action>();
	private String mTitle = "";
	private soundfun.ui.Panel mPanel;
	
	public PluginInfo() {}
	
	public PluginInfo(PluginInfo copy) throws CloneNotSupportedException {
		this.mTitle = new String(copy.mTitle);
		this.mPanel = (Panel) copy.clone();
	}
	
	/*
	 * Title of the plugin as seen by the user.
	 */
	public void setTitle(String title) {
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}

	public soundfun.ui.Panel getPanel() {
		return mPanel;
	}

	public void setPanel(soundfun.ui.Panel panel) {
		this.mPanel = panel;
	}
	
	/*
	 * Set which button this instance of
	 * PluginInfo was last bound to.
	 */
	public void setLastAssignedButton(String button) {
		mLastAssignedButton = button;
	}
	
	/*
	 * Get which button this instance of
	 * PluginInfo was last bound to.
	 */
	public String getLastAssignedButton() {
		return mLastAssignedButton;
	}
	
	/*
	 * Register an instance of Action to
	 * accept events as they are passed in.
	 */
	public void registerAction(Action a) {
		mActions.add(a);
		
	}
	
	/*
	 * Call every Action which has been registered.
	 */
	public void actionCalled(String evt) {
		for(Action a : mActions) {
			a.serialEvent(evt);
		}
	}
}
