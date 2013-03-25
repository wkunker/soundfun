package soundfun.ui;

import javax.swing.JToggleButton;

public class ToggleButton extends JToggleButton {
	private static final long serialVersionUID = soundfun.util.Globals.API_VERSION;
	
	EventDispatcher mDispatcher = new EventDispatcher();
	
	public ToggleButton(EventListener listener) {
		super();
		this.addActionListener(mDispatcher);
		mDispatcher.addListener(listener);
	}
}
