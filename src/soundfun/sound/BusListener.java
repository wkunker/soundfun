package soundfun.sound;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;
import org.gstreamer.Message;
import org.gstreamer.State;
import org.gstreamer.TagList;

/*
 * Captures all messages from a Gst Bus.
 */
public interface BusListener {
	/*
	 * Called when a GStreamer bus assigned to this action
	 * has received a message that the duration of the bin/pipeline
	 * has changed.
	 */
	void durationChanged(GstObject arg0, Format arg1, long arg2);

	/*
	 * Called when a GStreamer bus assigned to this action
	 * has received a message that the bin/pipeline has
	 * reached the end of its stream.
	 */
	void endOfStream(GstObject arg0);

	/*
	 * Called when a Gstreamer bus assigned to this action
	 * has received a message that a segment in the bin/pipeline
	 * has started.
	 */
	void segmentStart(GstObject arg0, Format arg1, long arg2);

	void asyncDone(GstObject source);

	void segmentDone(GstObject source, Format format, long position);

	void errorMessage(GstObject source, int code, String message);

	void infoMessage(GstObject source, int code, String message);

	void busMessage(Bus bus, Message message);

	void bufferingData(GstObject source, int percent);

	void stateChanged(GstObject source, State current);

	void tagsFound(GstObject source, TagList tagList);

	void warningMessage(GstObject source, int code, String message);
}
