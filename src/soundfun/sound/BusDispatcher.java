package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;
import org.gstreamer.Message;
import org.gstreamer.State;
import org.gstreamer.TagList;

import soundfun.util.Dispatcher;

/*
 * A bus dispatcher represents an multi-event dispatcher for
 * a single GStreamer bus.
 */
class BusDispatcher extends Dispatcher<BusListener> {
	private Bus mBus = null;
	
	private BusAsyncDoneListener mBusAsyncDoneListener = null;
	private BusDurationListener mBusDurationListener = null;
	private BusEosListener mBusEosListener = null;
	private BusSegmentStartListener mBusSegmentStartListener = null;
	private BusBufferingListener mBusBufferingListener = null;
	private BusErrorListener mBusErrorListener = null;
	private BusInfoListener mBusInfoListener = null;
	private BusMessageListener mBusMessageListener = null;
	private BusSegmentDoneListener mBusSegmentDoneListener = null;
	BusStateChangedListener mBusStateChangedListener = null;
	BusTagListener mBusTagListener = null;
	BusWarningListener mBusWarningListener = null;

	public BusDispatcher(org.gstreamer.Bus bus) {
		mBus = bus;
		
		/*
		 * Initialize all bus listeners to this BusDispatcher.
		 */
		
		mBusSegmentStartListener = new BusSegmentStartListener(this);
		mBusAsyncDoneListener = new BusAsyncDoneListener(this);
		mBusBufferingListener = new BusBufferingListener(this);
		mBusDurationListener = new BusDurationListener(this);
		mBusEosListener = new BusEosListener(this);
		mBusErrorListener = new BusErrorListener(this);
		mBusInfoListener = new BusInfoListener(this);
		mBusMessageListener = new BusMessageListener(this);
		mBusSegmentDoneListener = new BusSegmentDoneListener(this);
		mBusSegmentStartListener = new BusSegmentStartListener(this);
		mBusStateChangedListener = new BusStateChangedListener(this);
		mBusTagListener = new BusTagListener(this);
		mBusWarningListener = new BusWarningListener(this);
		
		/*
		 * Connect the bus to the bus listeners.
		 */
		mBus.connect(mBusAsyncDoneListener);
		mBus.connect(mBusBufferingListener);
		mBus.connect(mBusDurationListener);
    	mBus.connect(mBusEosListener);
    	mBus.connect(mBusErrorListener);
    	mBus.connect(mBusInfoListener);
    	mBus.connect(mBusMessageListener);
    	mBus.connect(mBusSegmentDoneListener);
    	mBus.connect(mBusSegmentStartListener);
    	mBus.connect(mBusStateChangedListener);
    	mBus.connect(mBusTagListener);
    	mBus.connect(mBusWarningListener);
	}
	
	/*
	 * Call to clean up the class before shutting down.
	 * Failing to do so may cause unexpected GStreamer behavior.
	 */
	public void dispose() {
		mBus.disconnect(mBusAsyncDoneListener);
		mBus.disconnect(mBusBufferingListener);
		mBus.disconnect(mBusDurationListener);
    	mBus.disconnect(mBusEosListener);
    	mBus.disconnect(mBusErrorListener);
    	mBus.disconnect(mBusInfoListener);
    	mBus.disconnect(mBusMessageListener);
    	mBus.disconnect(mBusSegmentDoneListener);
    	mBus.disconnect(mBusSegmentStartListener);
    	mBus.disconnect(mBusStateChangedListener);
    	mBus.disconnect(mBusTagListener);
    	mBus.disconnect(mBusWarningListener);
    	mBusAsyncDoneListener = null;
		mBusBufferingListener = null;
		mBusDurationListener = null;
    	mBusEosListener = null;
    	mBusErrorListener = null;
    	mBusInfoListener = null;
    	mBusMessageListener = null;
    	mBusSegmentDoneListener = null;
    	mBusSegmentStartListener = null;
    	mBusStateChangedListener = null;
    	mBusTagListener = null;
    	mBusWarningListener = null;
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

	public void segmentDone(GstObject source, Format format, long position) {
		for(BusListener l : getListeners()) {
			l.segmentDone(source, format, position);
		}
	}

	public void asyncDone(GstObject source) {
		for(BusListener l : getListeners()) {
			l.asyncDone(source);
		}
	}

	public void errorMessage(GstObject source, int code, String message) {
		for(BusListener l : getListeners()) {
			l.errorMessage(source, code, message);
		}
	}

	public void infoMessage(GstObject source, int code, String message) {
		for(BusListener l : getListeners()) {
			l.infoMessage(source, code, message);
		}
	}

	public void busMessage(Bus bus, Message message) {
		for(BusListener l : getListeners()) {
			l.busMessage(bus, message);
		}
	}

	public void bufferingData(GstObject source, int percent) {
		for(BusListener l : getListeners()) {
			l.bufferingData(source, percent);
		}
	}

	public void stateChanged(GstObject source, State current) {
		for(BusListener l : getListeners()) {
			l.stateChanged(source, current);
		}
	}

	public void tagsFound(GstObject source, TagList tagList) {
		for(BusListener l : getListeners()) {
			l.tagsFound(source, tagList);
		}
	}

	public void warningMessage(GstObject source, int code, String message) {
		for(BusListener l : getListeners()) {
			l.warningMessage(source, code, message);
		}
	}
}
