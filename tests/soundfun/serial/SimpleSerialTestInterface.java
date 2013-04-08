package soundfun.serial;

import soundfun.serial.SerialInterface;
import soundfun.util.Log;

public class SimpleSerialTestInterface implements SerialInterface {

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
        }
}
