package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;

import soundfun.sound.BusDispatcher;

public class BusEosListener implements Bus.EOS {
	BusDispatcher mBusDispatcher = null;
	
	public BusEosListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void endOfStream(GstObject arg0) {
		mBusDispatcher.endOfStream(arg0);
	}
	
}
