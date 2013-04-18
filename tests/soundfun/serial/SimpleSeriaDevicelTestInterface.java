package soundfun.serial;

import soundfun.util.Log;

public class SimpleSeriaDevicelTestInterface implements SerialInterface {
    SimpleSerialDeviceTest mMainClass = null;
    
    public SimpleSeriaDevicelTestInterface(SimpleSerialDeviceTest mainClass) {
        mMainClass = mainClass;
    }

    @Override
    public void serialEvent(char data) {
        Log.logTestMsg(this, String.valueOf(data));
        
        mMainClass.serialEvent(data);
    }

    @Override
    public void serialConnection(boolean connected) {
        if(connected) {
            Log.logTestMsg(this, "Serial connected.");
        } else {
            Log.logTestMsg(this, "Serial could not connect.");
        }
        
        mMainClass.serialConnection(connected);
    }
}
