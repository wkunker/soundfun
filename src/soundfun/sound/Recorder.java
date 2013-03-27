package soundfun.sound;

import org.gstreamer.ElementFactory;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

import soundfun.util.Globals;

/*
 * Handles a single Pipeline for the purpose
 * of recording audio. The intended eventual goal of
 * the class is to handle problems that come
 * along with recording audio, such as latency,
 * or grabbing audio playback from the OS.
 * 
 * The constructor initializes the pipeline and
 * contained elements.
 */
public class Recorder extends Pipe {
	private BusDispatcher mBusDispatcher = null;
	
	Recorder(String outputFile) {
		mPipeline = new Pipeline();
		// TODO Make a more elegant solution to the incrementing "garbage"
		mAudioSrc = ElementFactory.make("autoaudiosrc", "audio_src_" + Globals.autoIncrementAsString());
		mAudioConvert = ElementFactory.make("audioconvert", "audio_convert_" + Globals.autoIncrementAsString());
		mLameEncoder = ElementFactory.make("lame", "lame_encoder_" + Globals.autoIncrementAsString());
		mFileSink = ElementFactory.make("filesink", "file_sink_" + Globals.autoIncrementAsString());
    	mPipeline.add(mAudioSrc);
    	mPipeline.add(mAudioConvert);
    	mPipeline.add(mLameEncoder);
    	mPipeline.add(mFileSink);
    	mAudioSrc.link(mAudioConvert);
    	mAudioConvert.link(mLameEncoder);
    	mLameEncoder.link(mFileSink);
    	mFileSink.set("location", outputFile);
    	
    	// TODO Make the default bit rate load from a configuration file.
    	setBitrate(192); // Set a default bitrate.

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
		
		// Disable the Pipeline.
		mPipeline.stop();
		mPipeline.setState(State.NULL);
		
		// Unlink elements from each other.
		mLameEncoder.unlink(mFileSink);
		mAudioConvert.unlink(mLameEncoder);
		mAudioSrc.unlink(mAudioConvert);
    	
		// Remove elements from Pipeline.
    	mPipeline.remove(mFileSink);
    	mPipeline.remove(mAudioSrc);
    	mPipeline.remove(mAudioConvert);
    	mPipeline.remove(mLameEncoder);
    	
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
	 * If the Recorder has been recording up to this point,
	 * this will stop the recorder and output an audio file
	 * to the location specified in the constructor of this
	 * class.
	 * If the Recorder hasn't been recording up to this point,
	 * this won't do anything.
	 */
	@Override
	public void stop() {
		/*
		 * Stop the recording.
		 */
		mPipeline.setState(State.READY);
	}

	@Override
	public void start() {
		/*
		 * Start the recording.
		 */
		mPipeline.setState(State.PLAYING);
	}
}
