package soundfun.core;

import soundfun.plugins.PluginInfo;
import soundfun.serial.SerialInterface;
import soundfun.util.Log;

/*
 * Looks for all serial events and passes them off to the appropriate listener.
 * 
 * TODO Make the logic in this class more generic, then load a variable stating the number
 * of buttons on the controller board either from a configuration file containing a list
 * of controller boards, or from the controller board itself.
 */
public class SerialLogic implements SerialInterface {
	// Ensure that all serial events actually make sense, and correct as necessary.
	private boolean lastAPressed = false; // false for last released
	private boolean lastBPressed = false;
	private boolean lastCPressed = false;

	@Override
	public void serialEvent(char data) {
		if(data == 'a') {
			Log.logDbgMsg(this, "a");
			
			if(!lastAPressed) { // Probably a sensitive button release or similar.
				// Try to correct it by calling a button press first.
				Log.logMsg("A was released twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
				i.setLastAssignedButton("Button A");
				i.actionCalled("buttonPressed");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
			i.setLastAssignedButton("Button A");
			i.actionCalled("buttonReleased");
			lastAPressed = false;
		} else if(data == 'A') {
			Log.logDbgMsg(this, "A");
			
			if(lastAPressed) { // Probably a sensitive button press or similar.
				// Try to correct it by calling a button press first.
				Log.logMsg("A was pressed twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
				i.setLastAssignedButton("Button A");
				i.actionCalled("buttonReleased");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
			i.setLastAssignedButton("Button A");
			i.actionCalled("buttonPressed");
			lastAPressed = true;
		} else if(data == 'b') {
			Log.logDbgMsg(this, "b");
			
			if(!lastBPressed) { // Probably a sensitive button release or similar.
				// Try to correct it by calling a button press first.
				Log.logMsg("B was released twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
				i.setLastAssignedButton("Button B");
				i.actionCalled("buttonPressed");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
			i.setLastAssignedButton("Button B");
			i.actionCalled("buttonReleased");
			lastBPressed = false;
		} else if(data == 'B') {
			Log.logDbgMsg(this, "B");
			
			if(lastBPressed) { // Probably a sensitive button press or similar.
				// Try to correct it by calling a button press first.
				Log.logMsg("B was pressed twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
				i.setLastAssignedButton("Button B");
				i.actionCalled("buttonReleased");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
			i.setLastAssignedButton("Button B");
			i.actionCalled("buttonPressed");
			lastBPressed = true;
		} else if(data == 'c') {
			Log.logDbgMsg(this, "c");
			
			if(!lastCPressed) { // Probably a sensitive button release or similar.
				// Try to correct it by calling a button press first.
				Log.logMsg("C was released twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
				i.setLastAssignedButton("Button C");
				i.actionCalled("buttonPressed");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
			i.setLastAssignedButton("Button C");
			i.actionCalled("buttonReleased");
			lastCPressed = false;
		} else if(data == 'C') {
			Log.logDbgMsg(this, "C");
			
			if(lastCPressed) { // Probably a sensitive button press or similar.
				// Try to correct it by calling a button press first.
				Log.logMsg("C was pressed twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
				i.setLastAssignedButton("Button C");
				i.actionCalled("buttonReleased");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
			i.setLastAssignedButton("Button C");
			i.actionCalled("buttonPressed");
			lastCPressed = true;
		} else {
			Log.logErrMsg("Unknown signal was sent from serial... This is likely a bug in the serial communication.");
		}
	}

    @Override
    public void serialConnection(boolean connected) {
        if(connected) {
            Log.logMsg("Serial connected.");
        } else {
            Log.logMsg("Serial could not connect.");
        }
    }
}
