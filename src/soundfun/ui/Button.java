package soundfun.ui;

import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = soundfun.util.Globals.API_VERSION;
	
	EventDispatcher mDispatcher = new EventDispatcher();
	
	public Button(EventListener listener) {
		super();
		this.addActionListener(mDispatcher);
		mDispatcher.addListener(listener);
	}
}
