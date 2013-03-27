package soundfun.sound;

import org.gstreamer.Element;

/*
 * Represents a "pipe," or a segment of the pipeline.
 */
@Deprecated
public class PipeContainer {
	private Element mFileSrc = null;
	private Element mDecodeBin = null;
	private Element mAudioConvert = null;
	private Element mAudioResample = null;
	private Element mVolume = null;
	
	public PipeContainer() {}
	
	public void setFileSrc(Element fileSrc) {
		mFileSrc = fileSrc;
	}
	
	public void setDecodeBin(Element decodeBin) {
		mDecodeBin = decodeBin;
	}
	
	public void setAudioConvert(Element audioConvert) {
		mAudioConvert = audioConvert;
	}
	
	public void setAudioResample(Element audioResample) {
		mAudioResample = audioResample;
	}
	
	public void setVolume(Element volume) {
		mVolume = volume;
	}
	
	public Element getFileSrc() {
		return mFileSrc;
	}
	
	public Element getDecodeBin() {
		return mDecodeBin;
	}
	
	public Element getAudioConvert() {
		return mAudioConvert;
	}
	
	public Element getAudioResample() {
		return mAudioResample;
	}
	
	public Element getVolume() {
		return mVolume;
	}
}
