package soundfun.serial;

/*
 * Interface for handling received serial data.
 */
public interface SerialInterface {
	/*
	 * Called every time a byte of serial data is received.
	 */
	public void serialEvent(char data);
        
        /*
         * Called when a serial connection event has occured.
         * boolean connected is true when a connection occured,
         * otherwise false if a connection did not occur after
         * probing all ports.
         */
        public void serialConnection(boolean connected);
}
