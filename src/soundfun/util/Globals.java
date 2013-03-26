package soundfun.util;

public class Globals {
	private static long mAutoIncrement = 0;
	
	/*
	 * The global API version for SoundFun. This should be incremented any time
	 * a change occurs to the API itself, not any underlying implementation.
	 */
	public static final long API_VERSION = 1L;
	
	/*
	 * Enables/disables extra code helpful to finding problems
	 * and ensuring consistency throughout the program.
	 * 
	 * True for debug enabled, false for debug disabled.
	 */
	public static final boolean DEBUG_ENABLED = true;
	
	/*
	 * Log file location.
	 */
	public static final String LOG_FILE = "log";
	
	public static final long autoIncrement() {
		return mAutoIncrement++;
	}
}
