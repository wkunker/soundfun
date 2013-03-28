package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;

public class BusSegmentDoneListener implements Bus.SEGMENT_DONE {
	BusDispatcher mDispatcher = null;
	
	public BusSegmentDoneListener(BusDispatcher dispatcher) {
		mDispatcher = dispatcher;
	}

	@Override
	public void segmentDone(GstObject source, Format format, long position) {
		mDispatcher.segmentDone(source, format, position);
	}
	
}
