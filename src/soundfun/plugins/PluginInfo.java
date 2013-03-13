package soundfun.plugins;

import java.util.ArrayList;

import soundfun.ui.Panel;

/*
 * Identification for each plugin. An instance of PluginInfo is
 * returned from the setup() method in the plugin to SoundFun.
 */
public class PluginInfo {
	public PluginInfo() {}
	
	public PluginInfo(PluginInfo copy) throws CloneNotSupportedException {
		this.mTitle = new String(copy.mTitle);
		this.mPanel = (Panel) copy.clone();
	}
	
	private soundfun.ui.Panel mPanel;
	
	/*
	 * Title of the plugin as seen by the user.
	 */
	private String mTitle = "";
	
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
	
	private String mLastAssignedButton = null;
	private ArrayList<Action> mActions = new ArrayList<Action>();
	
	public void setLastAssignedButton(String button) {
		mLastAssignedButton = button;
	}
	
	public String getLastAssignedButton() {
		return mLastAssignedButton;
	}
	
	public void registerAction(Action a) {
		mActions.add(a);
	}
	
	public void actionCalled(String evt) {
		for(Action a : mActions) {
			a.serialEvent(evt);
		}
	}
}
