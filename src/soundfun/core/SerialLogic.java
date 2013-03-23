package soundfun.core;

import soundfun.plugins.PluginInfo;
import soundfun.serial.SerialInterface;
import soundfun.util.Log;

/*
 * Looks for all serial events and passes them off to the appropriate listener.
 */
public class SerialLogic implements SerialInterface {
	// Ensure that all serial events actually make sense, and correct as necessary.
	private boolean lastAPressed = false; // false for last released
	private boolean lastBPressed = false;
	private boolean lastCPressed = false;

	@Override
	public void serialEvent(char data) {
		if(data == 'a') {
			Log.logDebugMessage("a");
			
			if(!lastAPressed) { // Probably a sensitive button release or similar.
				// Try to correct it by calling a button press first.
				Log.logMessage("A was released twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
				i.setLastAssignedButton("Button A");
				i.actionCalled("buttonPressed");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
			i.setLastAssignedButton("Button A");
			i.actionCalled("buttonReleased");
			lastAPressed = false;
		} else if(data == 'A') {
			Log.logDebugMessage("A");
			
			if(lastAPressed) { // Probably a sensitive button press or similar.
				// Try to correct it by calling a button press first.
				Log.logMessage("A was pressed twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
				i.setLastAssignedButton("Button A");
				i.actionCalled("buttonReleased");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button A");
			i.setLastAssignedButton("Button A");
			i.actionCalled("buttonPressed");
			lastAPressed = true;
		} else if(data == 'b') {
			Log.logDebugMessage("b");
			
			if(!lastBPressed) { // Probably a sensitive button release or similar.
				// Try to correct it by calling a button press first.
				Log.logMessage("B was released twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
				i.setLastAssignedButton("Button B");
				i.actionCalled("buttonPressed");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
			i.setLastAssignedButton("Button B");
			i.actionCalled("buttonReleased");
			lastBPressed = false;
		} else if(data == 'B') {
			Log.logDebugMessage("B");
			
			if(lastBPressed) { // Probably a sensitive button press or similar.
				// Try to correct it by calling a button press first.
				Log.logMessage("B was pressed twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
				i.setLastAssignedButton("Button B");
				i.actionCalled("buttonReleased");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button B");
			i.setLastAssignedButton("Button B");
			i.actionCalled("buttonPressed");
			lastBPressed = true;
		} else if(data == 'c') {
			Log.logDebugMessage("c");
			
			if(!lastCPressed) { // Probably a sensitive button release or similar.
				// Try to correct it by calling a button press first.
				Log.logMessage("C was released twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
				i.setLastAssignedButton("Button C");
				i.actionCalled("buttonPressed");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
			i.setLastAssignedButton("Button C");
			i.actionCalled("buttonReleased");
			lastCPressed = false;
		} else if(data == 'C') {
			Log.logDebugMessage("C");
			
			if(lastCPressed) { // Probably a sensitive button press or similar.
				// Try to correct it by calling a button press first.
				Log.logMessage("C was pressed twice in a row. Correcting...");
				
				PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
				i.setLastAssignedButton("Button C");
				i.actionCalled("buttonReleased");
			}
			
			PluginInfo i = StateManager.getSingleton().getCurrentStates().get("Button C");
			i.setLastAssignedButton("Button C");
			i.actionCalled("buttonPressed");
			lastCPressed = true;
		}
	}
}
