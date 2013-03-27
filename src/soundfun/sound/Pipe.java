package soundfun.sound;

import org.gstreamer.Pipeline;

/*
 * Base class which represents a top-level GStreamer pipeline,
 * but in a way that's more familiar to SoundFun.
 */
abstract class Pipe {
	protected BusDispatcher mBusDispatcher = null;

	protected Pipeline mPipeline = null;
	

	public Pipe() {
		super();
	}
	
	public org.gstreamer.Pipeline getGstPipeline() {
		return mPipeline;
	}
	
	public abstract void dispose();
	
	public abstract void stop();
	
	public abstract void start();

	public abstract void addBusListener(BusListener listener);

	public abstract void removeBusListener(BusListener listener);
}