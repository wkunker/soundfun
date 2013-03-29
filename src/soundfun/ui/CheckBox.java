package soundfun.ui;

import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox {
	private static final long serialVersionUID = soundfun.util.Globals.API_VERSION;
	
	EventDispatcher mDispatcher = new EventDispatcher();
	
	public CheckBox(EventListener listener) {
		super();
		this.addActionListener(mDispatcher);
		mDispatcher.addListener(listener);
	}
}
