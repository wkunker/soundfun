package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Message;

import soundfun.sound.BusDispatcher;

public class BusMessageListener implements Bus.MESSAGE {
	BusDispatcher mBusDispatcher = null;
	
	public BusMessageListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void busMessage(Bus bus, Message message) {
		mBusDispatcher.busMessage(bus, message);
	}
}
