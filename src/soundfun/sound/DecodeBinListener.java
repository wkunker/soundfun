package soundfun.sound;
import org.gstreamer.Element;
import org.gstreamer.Element.PAD_ADDED;
import org.gstreamer.Pad;

public class DecodeBinListener implements PAD_ADDED {
	Pad sink_audioConvert = null;
	
	DecodeBinListener(Pad audioConvertSinkPad) {
		sink_audioConvert = audioConvertSinkPad;
	}

	@Override
	public void padAdded(Element arg0, Pad arg1) {
		arg1.link(sink_audioConvert);
	}

}
