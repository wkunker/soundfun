package soundfun.core;

import soundfun.plugins.PluginInfo;
import soundfun.serial.SerialInterface;

/*
 * Looks for all serial events and passes them off to the appropriate listener.
 */
public class SerialLogic implements SerialInterface {

	@Override
	public void serialEvent(char data) {
		if(data == 'a') {
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
			i.setLastAssignedButton("Button A");
			i.actionCalled("buttonReleased");
		} else if(data == 'A') {
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
			i.setLastAssignedButton("Button A");
			i.actionCalled("buttonPressed");
		} else if(data == 'b') {
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
			i.setLastAssignedButton("Button B");
			i.actionCalled("buttonReleased");
		} else if(data == 'B') {
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
			i.setLastAssignedButton("Button B");
			i.actionCalled("buttonPressed");
		} else if(data == 'c') {
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
			i.setLastAssignedButton("Button C");
			i.actionCalled("buttonReleased");
		} else if(data == 'C') {
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
			i.setLastAssignedButton("Button C");
			i.actionCalled("buttonPressed");
		}
	}
}
