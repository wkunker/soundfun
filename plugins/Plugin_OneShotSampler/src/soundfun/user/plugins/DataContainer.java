package soundfun.user.plugins;

import soundfun.ui.Panel;

/*
 * Holds whatever needs to be passed between classes,
 * so states can be maintained and modified through accessors.
 */
public class DataContainer {	
	private Panel mPanel = null;
	
	public DataContainer() {
		setPanel(null);
	}

	public Panel getPanel() {
		return mPanel;
	}

	public void setPanel(Panel mPanel) {
		this.mPanel = mPanel;
	}
}
