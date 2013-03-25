package soundfun.ui;

import javax.swing.JRadioButton;

public class RadioButton extends JRadioButton {
	private static final long serialVersionUID = soundfun.util.Globals.API_VERSION;
	
	EventDispatcher mDispatcher = new EventDispatcher();
	
	public RadioButton(EventListener listener) {
		super();
		this.addActionListener(mDispatcher);
		mDispatcher.addListener(listener);
	}
}
