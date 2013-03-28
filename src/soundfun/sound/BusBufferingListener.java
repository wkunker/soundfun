package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;
import soundfun.sound.BusDispatcher;

public class BusBufferingListener implements Bus.BUFFERING {
	BusDispatcher mBusDispatcher = null;
	
	public BusBufferingListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void bufferingData(GstObject source, int percent) {
		mBusDispatcher.bufferingData(source, percent);
		
	}
}
