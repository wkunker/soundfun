package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;

import soundfun.sound.BusDispatcher;

public class BusAsyncDoneListener implements Bus.ASYNC_DONE {
	BusDispatcher mBusDispatcher = null;
	
	public BusAsyncDoneListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void asyncDone(GstObject source) {
		mBusDispatcher.asyncDone(source);
	}
}
