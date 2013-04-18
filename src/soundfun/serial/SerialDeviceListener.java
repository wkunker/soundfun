package soundfun.serial;

/**
 * SerialDeviceListener - Listen to a device and send signals to the injected
 * SerialInterface instance.
 */
public interface SerialDeviceListener {

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public void close();

    /*
     * Allocate objects as necessary, find and open the port, start the listener.
     * 
     * @param serialInterface the SerialInterface implementation that serial event
     * signals will be passed to when they occur.
     */
    public void initialize(SerialInterface serialInterface, SerialOptions options) throws Exception;
}
