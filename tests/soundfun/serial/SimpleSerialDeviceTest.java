package soundfun.serial;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
/*
 * Tests using the SerialDeviceListener interface implementation provided
 * by SoundFun (for use with SoundFun devices). This fails unless a SoundFun
 * device is connected.
 */
@RunWith(JUnit4.class)
public class SimpleSerialDeviceTest {
    SimpleSeriaDevicelTestInterface mInterface = null;
    
    @Test
    public void test() {
        try{System.in.read();}
        catch(Exception e){}
        
        // Initialize the SerialManager class.
        SerialManager sm = SerialManager.getSingleton();

        mInterface = new SimpleSeriaDevicelTestInterface(this);
        SerialOptions options = sm.createSerialOptions();
        options.addPort("/dev/ttyUSB0"); // Linux
        options.addPort("/dev/ttyACM0"); // Linux

        try {
            sm.startSerialListener(mInterface, options);
        } catch (Exception ex) {
            Logger.getLogger(SimpleSerialDeviceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals(true, false);
        }
    }
    
    public void serialConnection(boolean connected) {
        assertEquals(true, connected);
    }

    void serialEvent(char data) {
    }
}
