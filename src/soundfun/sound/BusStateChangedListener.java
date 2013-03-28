package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;
import org.gstreamer.State;

import soundfun.sound.BusDispatcher;

public class BusStateChangedListener implements Bus.STATE_CHANGED {
	BusDispatcher mBusDispatcher = null;
	
	public BusStateChangedListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void stateChanged(GstObject source, State old, State current,
			State pending) {
		mBusDispatcher.stateChanged(source, current);
	}
}
