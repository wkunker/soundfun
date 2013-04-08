package soundfun.serial;

import java.util.ArrayList;
import java.util.List;

/*
 * Common serial options which are passed to the serial communications
 * upon initialization. This class should be created via SerialManager.createSerialOptions()
 */
public class SerialOptions {
    private List<String> mPorts = null;
    private int mTimeout;
    private int mDataRate;
    
    SerialOptions() {
        mPorts = new ArrayList<>();
        
        // Set defaults.
        mTimeout = 2000;
        mDataRate = 9600;
    }
    
   /*
    * Add a port to the list that the serial communications will probe from
    * when it's initialized. No ports are probed by default.
    */
    public boolean addPort(String portName) {
        return mPorts.add(portName);
    }
    
    /*
     * Remove a port from the list the serial communications will try to probe
     * when it's initialized.
     */
    public boolean removePort(String portName) {
        return mPorts.remove(portName);
    }
    
    /*
     * Return any ports added in this instance of SerialOptions.
     * This is for internal use by the SerialListener or similar.
     */
    List<String> _getPorts() {
        return mPorts;
    }
    
    /*
     * Set the timeout manually.
     */
    public void setTimeout(int timeout) {
        mTimeout = timeout;
    }
    
    /*
     * Return the default timeout if none has been set manually.
     */
    public int getTimeout() {
        return mTimeout;
    }
    
    /*
     * Set the data rate manually.
     */
    public void setDataRate(int datarate) {
        mDataRate = datarate;
    }
    
    /*
     * Return the default data rate if none has been set manually.
     */
    public int getDataRate() {
        return mDataRate;
    }
}
