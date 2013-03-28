package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;
import org.gstreamer.TagList;

import soundfun.sound.BusDispatcher;

public class BusTagListener implements Bus.TAG {
	BusDispatcher mBusDispatcher = null;
	
	public BusTagListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void tagsFound(GstObject source, TagList tagList) {
		mBusDispatcher.tagsFound(source, tagList);
	}
}
