package soundfun.util;

public class Globals {
	private static long mAutoIncrement = 0;
	
	/*
	 * The global API version for SoundFun. This should be incremented any time
	 * a change occurs to the API itself, not any underlying implementation.
	 */
	public static final long API_VERSION = 1L;
	
	public static final long autoIncrement() {
		return mAutoIncrement++;
	}
}
