package soundfun.sound;

import org.gstreamer.Gst;

public class SoundManager {
	private static SoundManager mSingleton = null;
	
	public static SoundManager getSingleton() {
		if(mSingleton == null) {
			mSingleton = new SoundManager();
		}
		
		return mSingleton;
	}
	
	private SoundManager() {
		Gst.init();
	}
	
	/*
	 * Creates and returns a new instance of Recorder,
	 * which handles most desired recording functionality.
	 */
	public Recorder createRecorder(String outputFile) {
		return new Recorder(outputFile);
	}
	
	public Player createPlayer(String inputFile) {
		// TODO Replace the auto-increment with something more graceful.
		return new Player(inputFile, "player_" + soundfun.util.Globals.autoIncrementAsString());
	}
}
