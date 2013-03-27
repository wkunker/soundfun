package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;

import soundfun.util.Dispatcher;

/*
 * A bus dispatcher represents an multi-event dispatcher for
 * a single GStreamer bus.
 */
class BusDispatcher extends Dispatcher<BusListener> {
	private Bus mBus = null;
	private BusDurationListener mBusDurationListener = null;
	private BusEosListener mBusEosListener = null;
	private BusSegmentStartListener mBusSegmentStartListener = null;

	public BusDispatcher(org.gstreamer.Bus bus) {
		mBus = bus;
		
		/*
		 * Initialize all bus listeners to this BusDispatcher.
		 */
		mBusDurationListener = new BusDurationListener(this);
		mBusEosListener = new BusEosListener(this);
		mBusSegmentStartListener = new BusSegmentStartListener(this);
		
		/*
		 * Connect the bus to the bus listeners.
		 */
		mBus.connect(mBusDurationListener);
    	mBus.connect(mBusEosListener);
    	mBus.connect(mBusSegmentStartListener);
	}
	
	/*
	 * Call to clean up the class before shutting down.
	 * Failing to do so may cause unexpected GStreamer behavior.
	 */
	public void dispose() {
		mBus.disconnect(mBusDurationListener);
    	mBus.disconnect(mBusEosListener);
    	mBus.disconnect(mBusSegmentStartListener);
    	mBusDurationListener = null;
    	mBusEosListener = null;
    	mBusSegmentStartListener = null;
	}

	
	/*********************
	 * Pass the callbacks.
	 ********************/
	
	public void durationChanged(GstObject arg0, Format arg1, long arg2) {
		for(BusListener l : getListeners()) {
			l.durationChanged(arg0, arg1, arg2);
		}
	}

	public void endOfStream(GstObject arg0) {
		for(BusListener l : getListeners()) {
			l.endOfStream(arg0);
		}
	}

	public void segmentStart(GstObject arg0, Format arg1, long arg2) {
		for(BusListener l : getListeners()) {
			l.segmentStart(arg0, arg1, arg2);
		}
	}
}
