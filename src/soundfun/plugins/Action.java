package soundfun.plugins;

/*
 * When a Plugin implementation extends Action, it signifies itself
 * as a SoundFun action. As an Action, certain events must be handled.
 */
public abstract class Action {
	/*
	 * Called whenever a serial event this action is bound to has occured.
	 * 
	 * Possible values for evt: "buttonPressed", "buttonReleased"
	 */
	public abstract void serialEvent(String evt);
}
