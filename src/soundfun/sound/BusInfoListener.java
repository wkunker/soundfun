package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;

import soundfun.sound.BusDispatcher;

public class BusInfoListener implements Bus.INFO {
	BusDispatcher mBusDispatcher = null;
	
	public BusInfoListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void infoMessage(GstObject source, int code, String message) {
		mBusDispatcher.infoMessage(source, code, message);
	}
}
