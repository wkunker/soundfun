package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;

import soundfun.sound.BusDispatcher;

public class BusErrorListener implements Bus.ERROR {
	BusDispatcher mBusDispatcher = null;
	
	public BusErrorListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void errorMessage(GstObject source, int code, String message) {
		mBusDispatcher.errorMessage(source, code, message);
	}
}
