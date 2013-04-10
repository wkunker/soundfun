package soundfun.serial;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
/*
 * Various tests using the SerialDeviceListener interface implementation provided
 * by SoundFun (for use with SoundFun devices).
 */
public class SimpleSerialTest {
    SimpleSerialTestInterface mInterface = null;
    
    @Test(timeout = 10000) // Wait up to 10 seconds in case it's a slow computer.
    public void test() {
        System.out.println("Plug in your SoundFun device then press Enter to continue (test will fail if no input within 10 seconds)...");  
        try{System.in.read();}
        catch(Exception e){}
        
        // Initialize the SerialManager class.
        SerialManager sm = SerialManager.getSingleton();

        mInterface = new SimpleSerialTestInterface(this);
        SerialOptions options = sm.createSerialOptions();

        try {
            sm.startSerialListener(mInterface, options);
        } catch (Exception ex) {
            Logger.getLogger(SimpleSerialTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void serialConnection(boolean connected) {
        assertEquals(true, connected);
    }
}
