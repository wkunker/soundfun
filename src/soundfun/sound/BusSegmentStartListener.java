package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;

import soundfun.sound.BusDispatcher;

public class BusSegmentStartListener implements Bus.SEGMENT_START {
	BusDispatcher mBusDispatcher = null;
	
	public BusSegmentStartListener(BusDispatcher busDispatcher) {
		mBusDispatcher = busDispatcher;
	}

	@Override
	public void segmentStart(GstObject arg0, Format arg1, long arg2) {
		mBusDispatcher.segmentStart(arg0, arg1, arg2);
	}
}
