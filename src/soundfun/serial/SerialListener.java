package soundfun.serial;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

/*
 * Class for talking to the serial device.
 * 
 * Currently uses Java RXTX although that could be
 * changed in the future by replacing this class.
 */
class SerialListener implements SerialPortEventListener {
	SerialPort serialPort;
	long msStartTime = -1;
	String mSerialBuffer = null; // set to null when not in use
	soundfun.serial.SerialManager mSerialManager = null;
	
        /** The port we're normally going to use. */
	private Vector<String> mPortNames = null;

	/** Buffered input stream from the port */
	private InputStream input;
	
	/** Milliseconds to block while waiting for port open */
	private int mTimeout;
	
	/** Default bits per second for COM port. */
	private int mDataRate;
	
	@SuppressWarnings("unused")
	private SerialListener() {} // Not to be called, ever.
	
	/*
	 * Setup the initial configuration.
	 */
	SerialListener(Vector<String> port_names, int timeout, int datarate) {
		mPortNames = port_names;
		mTimeout = timeout;
		mDataRate = datarate;
	}
	
	/*
	 * Allocate objects as necessary, find and open the port, start the listener.
	 */
	public void initialize() {
		// SerialManager is used to provide serial data to the user without
		mSerialManager = soundfun.serial.SerialManager.getSingleton();
		
		// Setup to find the correct port.
		CommPortIdentifier portId = null;
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		// Iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : mPortNames) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					mTimeout);

			// set port parameters
			serialPort.setSerialPortParams(mDataRate,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				// TODO: Test available() since the data allocation is not guaranteed.
				int available = input.available();
				
				// Allocate the data.
				byte chunk[] = new byte[available];
				
				// Read the data from the serial InputStream.
				input.read(chunk, 0, available);
				
				// Convert the first byte of data to a char, then send it to the SerialManager instance.
				mSerialManager._serialEvent(soundfun.util.Conversions.byteToChar(chunk[0]));
			} catch (Exception e) {
				System.err.println(e.toString());
			}
			
		// TODO: See if other event types may be of help.
		}
	}
}
