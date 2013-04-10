package soundfun.serial;
import soundfun.util.Log;

public class SimpleSerialTestInterface implements SerialInterface {
    SimpleSerialTest mMainClass = null;
    
    public SimpleSerialTestInterface(SimpleSerialTest mainClass) {
        mMainClass = mainClass;
    }

    @Override
    public void serialEvent(char data) {
        System.out.println(data);
    }

    @Override
    public void serialConnection(boolean connected) {
        if(connected) {
            Log.logMsg("Serial connected.");
        } else {
            Log.logMsg("Serial could not connect.");
        }
        
        mMainClass.serialConnection(connected);
    }
}
