package soundfun.serial;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Basic test of the serial interface.
 * It doesn't work from jUnit as it terminates when the listener starts blocking.
 */
public class SimpleSerialTest {

	public static void test() {
		// Initialize the SerialManager class.
		SerialManager sm =
				SerialManager.getSingleton();
		
		SimpleSerialTestInterface i = new SimpleSerialTestInterface();
		sm.addSerialInterface(i);
                SerialOptions options = sm.createSerialOptions();
                
                try {
                    sm.startSerialListener(options);
                } catch (Exception ex) {
                    Logger.getLogger(SimpleSerialTest.class.getName()).log(Level.SEVERE, null, ex);
                }
	}
	
	public static void main(String[] args) throws Exception {
		test();
	}
}
