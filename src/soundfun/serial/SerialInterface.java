package soundfun.serial;

/*
 * Interface for handling received serial data.
 * TODO: Decide weather or not there is a purpose in an outside package accessing SerialInterface.
 */
public interface SerialInterface {
	/*
	 * Called every time a byte of serial data is received.
	 */
	public void serialEvent(char data);
}
