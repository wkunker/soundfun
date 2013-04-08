package soundfun.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Utility class for general event logging.
 * 
 * This class, like the rest of the soundfun.util package,
 * should be independent of anything else in the project.
 */
public class Log {
        private static Log mSingleton = null;
        
        private Log() {}
        
        public static Log getSingleton() {
            if(mSingleton == null) {
                mSingleton = new Log();
            }
            
            return mSingleton;
        }
    
	public void logMessage(String msg) {
		msg = "Log: " + msg;
                
                System.out.println(msg);
		
		try {
			BufferedWriter file_output = new BufferedWriter(new FileWriter(Globals.LOG_FILE, true));
			file_output.append(msg);
			file_output.newLine();
			file_output.close();
		} catch (IOException e) {
			logErrorMessage("Failed to open log file for ");
			e.printStackTrace();
		}
	}
        
        public static void logMsg(String msg) {
            Log.getSingleton().logMessage(msg);
        }
	
	public void logErrorMessage(String msg) {
		msg = "Error: " + msg;
		
		System.err.println(msg);
		
		try {
			BufferedWriter file_output = new BufferedWriter(new FileWriter(Globals.LOG_FILE, true));
			file_output.append(msg);
			file_output.newLine();
			file_output.close();
		} catch (IOException e) {
			logErrorMessage("Failed to open log file for ");
			e.printStackTrace();
		}
	}
        
        public static void logErrMsg(String msg) {
            Log.getSingleton().logErrorMessage(msg);
        }
	
	public void logDebugMessage(Object o, String msg) {
		msg = "Debug: (" + o.getClass().getName() + ")" + msg;
		
		if(Globals.DEBUG_ENABLED) {
			System.out.println(msg);
			
			try {
				BufferedWriter file_output = new BufferedWriter(new FileWriter(Globals.LOG_FILE, true));
				file_output.append(msg);
				file_output.newLine();
				file_output.close();
			} catch (IOException e) {
				logErrorMessage("Failed to open log file for ");
				e.printStackTrace();
			}
		}
	}
        
        public static void logDbgMsg(Object o, String msg) {
            Log.getSingleton().logDebugMessage(o, msg);
        }
}
