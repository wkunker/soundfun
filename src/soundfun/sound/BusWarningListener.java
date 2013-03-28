package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;
import soundfun.sound.BusDispatcher;

public class BusWarningListener implements Bus.WARNING {
	BusDispatcher mBusDispatcher = null;
	
	public BusWarningListener(BusDispatcher BusDispatcher) {
		mBusDispatcher = BusDispatcher;
	}

	@Override
	public void warningMessage(GstObject source, int code, String message) {
		mBusDispatcher.warningMessage(source, code, message);
	}
}
