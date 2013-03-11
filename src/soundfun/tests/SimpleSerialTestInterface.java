package soundfun.tests;

import soundfun.serial.SerialInterface;

public class SimpleSerialTestInterface implements SerialInterface {

	@Override
	public void serialEvent(char data) {
		System.out.println(data);
	}

}
