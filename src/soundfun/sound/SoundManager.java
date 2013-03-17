package soundfun.sound;

import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pad;
import org.gstreamer.Pipeline;

public class SoundManager {
	private static SoundManager mSingleton = null;
	private boolean MAIN_CALLED = false;
	
	public static SoundManager getSingleton() {
		if(mSingleton == null) {
			mSingleton = new SoundManager();
		}
		
		return mSingleton;
	}
	
	private SoundManager() {
		MAIN_CALLED = false;
		Gst.init();
	}
	
	private Pipeline mPipe = null;
	private int i = 0;
	private boolean addSampleToPipelineHasBeenCalled = false;
	
	public Pipeline getGstPipeline() {
		return mPipe;
	}
	
	public void play() {
		mPipe.play();
		if(!MAIN_CALLED) {
			Gst.main();
			MAIN_CALLED = true;
		}
	}
	
	public void removePipeFromPipeline(PipeContainer c) {
		
	}
	
	public Pad createSinkpad() throws Exception {
		Pad pad = null;
		pad = mPipe.getElementByName("mixadder").getRequestPad("sink%d");
		if(pad == null) {
			throw new Exception("FATAL ERROR: Unable to make sink pad.");
		}
		
		return pad;
	}
	
	/*
	 * decodebin, filesrc, audioconvert, and audioresample can all
	 * be accessed by calling getGstPipeline().getElementByName(element#)
	 * e.g. Element el = getGstPipeLine().getElementByName("audioconvert4");
	 */
	public PipeContainer addPipeToPipeline(String filePath) throws Exception {
		if(!addSampleToPipelineHasBeenCalled) {
			mPipe = Pipeline.launch("filesrc name=filesrc0 ! decodebin name=decodebin0 ! audioconvert name=audioconvert0 ! audioresample name=audioresample0 ! adder name=mixadder ! alsasink name=mainsink ! mixadder");
			mPipe.getElementByName("filesrc0").set("location", filePath);
			mPipe.getElementByName("mainsink").set("sync", false);
			
			addSampleToPipelineHasBeenCalled = true;
			
			PipeContainer c = new PipeContainer();
			c.setFileSrc(mPipe.getElementByName("filesrc0"));
			c.setDecodeBin(mPipe.getElementByName("decodebin0"));
			c.setAudioConvert(mPipe.getElementByName("audioconvert0"));
			c.setAudioResample(mPipe.getElementByName("audioresample0"));
			
			return c;
		}
		
		// The starting number of this is supposed to be 1.
		i++;
		
		Pad sinkpad = createSinkpad();
		
		Element decodeBin = ElementFactory.make("decodebin", "decodebin" + new Integer(i).toString());
		Element audioConvert = ElementFactory.make("audioconvert", "audioconvert" + new Integer(i).toString());
		Element audioResample = ElementFactory.make("audioresample", "audioresample" + new Integer(i).toString());
		
		Element fileSrc = ElementFactory.make("filesrc", "filesrc" + new Integer(i).toString());
		fileSrc.set("location", filePath);
		
		PipeContainer c = new PipeContainer();
		mPipe.add(fileSrc);		
		mPipe.add(decodeBin);
		mPipe.add(audioConvert);
		mPipe.add(audioResample);
		c.setFileSrc(fileSrc);
		c.setDecodeBin(decodeBin);
		c.setAudioConvert(audioConvert);
		c.setAudioResample(audioResample);
		
		Pad sink_decodeBin = decodeBin.getPad("sink");
		Pad sink_audioConvert = audioConvert.getPad("sink");
		Pad sink_audioResample = audioResample.getPad("sink");
		
		Pad src_fileSrc = fileSrc.getPad("src");
		Pad src_audioConvert = audioConvert.getPad("src");
		Pad src_audioResample = audioResample.getPad("src");
		
		if(sink_decodeBin == null) {
			throw new Exception("decodeBin sink pad null");
		}
		
		if(sink_audioConvert == null) {
			throw new Exception("audioConvert sink pad null");
		}
		
		if(sink_audioResample == null) {
			throw new Exception("audioResample sink pad null");
		}
		
		if(src_fileSrc ==  null) {
			throw new Exception("fileSrc src pad null");
		}
		
		if(src_audioConvert == null) {
			throw new Exception("audioConvert src pad null");
		}
		
		if(src_audioResample == null) {
			throw new Exception("audioResample src pad null");
		}
		
		DecodeBinListener l = new DecodeBinListener(sink_audioConvert);
		decodeBin.connect(l);
		
		src_fileSrc.link(sink_decodeBin);		
		
		src_audioConvert.link(sink_audioResample);
		src_audioResample.link(sinkpad);
		
		return c;
	}
}
