package soundfun.user.plugins;

import soundfun.ui.EventListener;

public class ButtonPressListener implements EventListener {
	Behavior mActionListener = null;
	
	public ButtonPressListener(Behavior p) {
		mActionListener = p;
	}

	@Override
	public void actionPerformed(String actionCommand) {
		mActionListener.actionPerformed(actionCommand);
	}
}
