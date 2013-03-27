package soundfun.sound;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.elements.PlayBin2;

/*
 * Handles a single Pipeline for the purpose
 * of playing audio. Constructor initializes
 * pipeline.
 */
public class Player extends Pipe {
	
	PlayBin2 mPlayBin = null;
	
	Player(String inputFile, String name) {
		super();
		
		mPipeline = new Pipeline();
		
		PlayBin2 playbin = new PlayBin2(name);
		mPipeline.add(playbin);
		mPlayBin = playbin;
		
		// TODO Make a more elegant solution to the incrementing "garbage"
		
    	mPlayBin.setInputFile(new File(inputFile));
    	
    	/*
    	 * Initialize the bus dispatcher so plugins can access the bus more easily.
    	 */
    	mBusDispatcher = new BusDispatcher(mPipeline.getBus());
	}
	
	/*
	 * It's necessary to call this in order to ensure
	 * the recorder is properly cleaned up. Failing to
	 * do so may result in unexpected behavior.
	 */
	@Override
	public void dispose() {
		// Disconnect and destroy the bus activity.
		mBusDispatcher.dispose();
		mBusDispatcher = null;
		
		// Disable the Pipeline.
		mPipeline.stop();
		mPipeline.setState(State.NULL);
    	
    	// Destroy the Pipeline.
    	mPipeline.dispose();
    	mPipeline = null;
	}
	
	/*
	 * Add a bus listener. As many can be added as desired.
	 */
	@Override
	public void addBusListener(BusListener listener) {
		mBusDispatcher.addListener(listener);
	}
	
	@Override
	public void removeBusListener(BusListener listener) {
		mBusDispatcher.removeListener(listener);
	}
	
	/*
	 * Pause and seek to the beginning of the clip.
	 */
	@Override
	public void stop() {
		mPipeline.seek(0, TimeUnit.MILLISECONDS);
		mPipeline.setState(State.PAUSED);
	}

	@Override
	public void start() {
		/*
		 * Start the recording.
		 */
		mPipeline.setState(State.PLAYING);
	}
}
