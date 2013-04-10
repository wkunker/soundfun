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
	private SerialDeviceListenerImpl mSerialListener = null;
        
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
	 * Start the serial listener. This will attempt to open the port
	 * and begin communication with the serial device.
	 */
	public void startSerialListener(SerialInterface serialInterface, SerialOptions options) throws Exception {
            mSerialListener = new SerialDeviceListenerImpl();
            mSerialListener.initialize(serialInterface, options);
	}
        
        /*
         * Start a customer serial device listener. This would typically be used
         * for testing mock device listeners, or to change serial functionality.
         */
        public void startSerialListener(SerialInterface serialInterface, SerialOptions options, SerialDeviceListenerImpl listener) throws Exception {
            mSerialListener = listener;
            mSerialListener.initialize(serialInterface, options);
	}
	
	public void stopSerialListener() {
            if(mSerialListener != null) {
                mSerialListener.close();
            }
	}
}
