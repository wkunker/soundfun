package soundfun.serial;

import java.util.ArrayList;
import java.util.List;

/*
 * Central class for performing serial operations with the device.
 */
public class SerialManager {
	// Singleton instance.
	private static SerialManager mSingleton = null;
	
	// Listeners which capture the serial signals.
	private List<SerialInterface> mSerialInterfaces = null;

	// Communicates with the serial device.
	private SerialDeviceListener mSerialListener = null;
        
        private List<String> mPorts = null;
	
	/*
	 * Constructor.
	 * Private accessor is part of Singleton pattern.
	 */
	private SerialManager() {
		mSerialInterfaces = new ArrayList<>();
                mPorts = new ArrayList<>();
        }
        
        /*
         * Creates a new instance of SerialOptions, which allows
         * the serial communications to be customized as desired,
         * without causing inconveniently large dependency injection.
         * 
         * The SerialOptions instance generated here is meant to be
         * immutable, and should be destroyed when it's no longer in use.
         */
        public SerialOptions createSerialOptions() {
            return new SerialOptions();
        }
	
	/*
	 * Returns the only possible instance of SerialManager.
	 */
	public static SerialManager getSingleton() {
		if(mSingleton == null) {
			mSingleton = new SerialManager();
                }
		
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
		if(!mSerialInterfaces.remove(i)) {
                    throw new Exception("Unable to remove specified SerialInterface implementation from SerialManager signal list since it does not exist.");
                }
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
         * Processes a serial connection event from inside the soundfun.serial package.
         */
        void _serialConnection(boolean connected) {
            for(int i = 0; i < mSerialInterfaces.size(); i++) {
                // Pass the signal to any connected serial interfaces.
                mSerialInterfaces.get(i).serialConnection(connected);
            }
        }
	
	/*
	 * Start the serial listener. This will attempt to open the port
	 * and begin communication with the serial device.
	 */
	public void startSerialListener(SerialOptions options) throws Exception {
            mSerialListener = new SerialDeviceListener();
            mSerialListener.initialize(options);
	}
	
	public void stopSerialListener() {
            if(mSerialListener != null) {
                mSerialListener.close();
            }
	}
}
