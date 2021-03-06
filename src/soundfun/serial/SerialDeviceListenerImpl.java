package soundfun.serial;

import gnu.io.CommPortIdentifier; 
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener; 
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;
import soundfun.util.Log;

/*
 * Class for talking to the serial device.
 * 
 * Currently uses Java RXTX although that could be
 * changed in the future by replacing this class.
 */
public final class SerialDeviceListenerImpl implements SerialPortEventListener, SerialDeviceListener {
	SerialPort serialPort;
	long msStartTime = -1;
	String mSerialBuffer = null; // set to null when not in use
	soundfun.serial.SerialManager mSerialManager = null;
        SerialInterface mSerialInterface = null;
	
        /** The port we're normally going to use. */
	private List<String> mPortNames = null;

	/** Buffered input stream from the port */
	private InputStream input;
	
	/** Milliseconds to block while waiting for port open */
	private int mTimeout;
	
	/** Default bits per second for COM port. */
	private int mDataRate;
        
        private Timer mTimeoutTimer = null;
	
	/*
	 * Allocate objects as necessary, find and open the port, start the listener.
	 */
        @Override
	public void initialize(SerialInterface serialInterface, SerialOptions options) throws Exception {
                mPortNames = options._getPorts();
                mTimeout = options.getTimeout();
                mDataRate = options.getDataRate();
                mSerialInterface = serialInterface;
                
                mTimeoutTimer = new Timer();
            
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
                    mSerialInterface.serialConnection(false);
                    throw new Exception("Could not find COM port.");
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
		} catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException e) {
			Log.logErrMsg(e.toString());
                        throw e;
		}
                
                // Successfully connected...
                
                
                //This will eventually be a
                // message sent by the device itself.
                mSerialInterface.serialConnection(true);
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
        @Override
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
         * 
         * This is overridden from RXTX SerialPortEventListener
	 */
	@Override
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
				mSerialInterface.serialEvent(soundfun.util.Conversions.byteToChar(chunk[0]));
			} catch (Exception e) {
				Log.logErrMsg(e.toString());
			}
			
		// TODO: See if other event types may be of help.
		}
	}

    public void ping() {
        /*
         * TODO: Implement serial request system; must be done hardware side as well as here.
         */
        
        
        // After serial event is sent, start the timer.

        mTimeoutTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                  // Serial connection failed; notify the dispatcher.
                  mSerialInterface.serialConnection(false);
                }
              }, 500);
    }
}
