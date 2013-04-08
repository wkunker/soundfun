package soundfun.user.plugins;

import soundfun.ui.EventListener;

public class ButtonPressListener implements EventListener {
	Behavior mActionListener = null;
	
	public ButtonPressListener() {
	}
        
        public void registerBehavior(Behavior behavior) {
            mActionListener = behavior;
        }

	@Override
	public void actionPerformed(String actionCommand) {
		mActionListener.actionPerformed(actionCommand);
	}
}
