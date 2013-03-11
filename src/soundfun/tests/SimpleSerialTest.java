package soundfun.tests;

/*
 * Basic test of the serial interface.
 * It doesn't work from jUnit as it terminates when the listener starts blocking.
 */
public class SimpleSerialTest {

	public static void test() {
		// Initialize the SerialManager class.
		soundfun.serial.SerialManager sm =
				soundfun.serial.SerialManager.getSingleton();
		
		SimpleSerialTestInterface i = new SimpleSerialTestInterface();
		sm.addSerialInterface(i);
		sm.startSerialListener();
	}
	
	public static void main(String[] args) throws Exception {
		test();
	}

}
