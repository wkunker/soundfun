package soundfun.serial;

import java.util.Vector;

/*
 * Central class for performing serial operations with the device.
 */
public class SerialManager {
	// Singleton instance.
	private static SerialManager mSingleton = null;
	
	// Listeners which form the "signals" of the MVC.
	private Vector<SerialInterface> mSerialInterfaces = null;

	// Communicates with the serial device.
	private SerialListener mSerialListener = null;
	
	/*
	 * Constructor.
	 * Private accessor is part of Singleton pattern.
	 */
	private SerialManager() {
		mSerialInterfaces = new Vector<SerialInterface>();
		
		Vector<String> portNames = new Vector<String>();
		portNames.add("/dev/tty.usbserial-A9007UX1"); // Mac OS X
		portNames.add("/dev/ttyUSB0"); // Linux
		portNames.add("/dev/ttyACM0"); // Linux
		portNames.add("COM3"); // Windows
		mSerialListener = new SerialListener(portNames, 2000, 9600);
	}
	
	/*
	 * Returns the only possible instance of SerialManager.
	 */
	public static SerialManager getSingleton() {
		if(mSingleton == null)
			mSingleton = new SerialManager();
		
		return mSingleton;
	}
	
	/*
	 * Add a serial interface to read signals sent as they are received over serial.
	 * As many interfaces can be added as desired.
	 */
	public void addSerialInterface(SerialInterface i) {
		mSerialInterfaces.add(i);
	}
	
	/*
	 * Attempt to remove the specified SerialInterface implementation.
	 * 
	 * Silent on success, Exception thrown on failure.
	 */
	public void removeSerialInterface(SerialInterface i) throws Exception {
		if(!mSerialInterfaces.remove(i))
			throw new Exception("Unable to remove specified SerialInterface implementation from SerialManager signal list since it does not exist.");
	}
	
	/*
	 * Processes a serial event sent from inside of the soundfun.serial package.
	 */
	void _serialEvent(char data) {
		for(int i = 0; i < mSerialInterfaces.size(); i++) {
			// Pass the signal to any connected serial interfaces.
			mSerialInterfaces.get(i).serialEvent(data);
		}
	}
	
	/*
	 * Start the serial listener. This will attempt to open the port
	 * and begin communication with the serial device.
	 */
	public void startSerialListener() {
		mSerialListener.initialize();
	}
	
	public void stopSerialListener() {
		mSerialListener.close();
	}
}
