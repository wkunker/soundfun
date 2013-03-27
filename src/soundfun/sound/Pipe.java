package soundfun.sound;

import org.gstreamer.Element;
import org.gstreamer.Pipeline;

/*
 * Base class which represents a top-level GStreamer pipeline,
 * but in a way that's more familiar to SoundFun.
 */
public abstract class Pipe {

	protected Pipeline mPipeline = null;
	protected Element mAudioSrc = null;
	protected Element mAudioConvert = null;
	protected Element mLameEncoder = null;
	protected Element mFileSink = null;
	private int mBitrate;

	public Pipe() {
		super();
	}

	public void setBitrate(int bitrate) {
		mLameEncoder.set("bitrate", bitrate);
		mBitrate = bitrate;
	}

	public int getBitrate() {
		return mBitrate;
	}
	
	public abstract void dispose();
	
	public abstract void stop();
	
	public abstract void start();

	public abstract void addBusListener(BusListener listener);

	public abstract void removeBusListener(BusListener listener);
}