package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;

public class BusDurationListener implements Bus.DURATION {
	BusDispatcher mDispatcher = null;
	
	public BusDurationListener(BusDispatcher dispatcher) {
		mDispatcher = dispatcher;
	}

	@Override
	public void durationChanged(GstObject arg0, Format arg1, long arg2) {
		mDispatcher.durationChanged(arg0, arg1, arg2);
	}
	
}
