package soundfun.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Utility class for general event logging.
 * 
 * This class, like the rest of the soundfun.util package,
 * is independent of anything else in the project.
 */
public class Log {	
	public static void logMessage(String msg) {
		msg = "Log: " + msg;
		
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
	
	public static void logErrorMessage(String msg) {
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
	
	public static void logDebugMessage(String msg) {
		msg = "Debug: " + msg;
		
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
}
