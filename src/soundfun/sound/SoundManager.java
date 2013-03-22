package soundfun.sound;

import java.util.ArrayList;
import java.util.List;

import org.gstreamer.Bin;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.GhostPad;
import org.gstreamer.Gst;
import org.gstreamer.Pad;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

public class SoundManager {
	private static SoundManager mSingleton = null;
	private List<PipeContainer> mPipeContainers = null;
	private Element mAdder = null;
	
	public static SoundManager getSingleton() {
		if(mSingleton == null) {
			mSingleton = new SoundManager();
		}
		
		return mSingleton;
	}
	
	private SoundManager() {
		Gst.init();
		mPipe = new Pipeline();
		
		mPipeContainers = new ArrayList<PipeContainer>();
		mAdder = null;
	}
	
	private Pipeline mPipe = null;
	private int i = 0;
	private boolean addSampleToPipelineHasBeenCalled = false;
	
	public Pipeline getGstPipeline() {
		return mPipe;
	}
	
	public List<PipeContainer> getPipeContainers() {
		return mPipeContainers;
	}
	
	public void removeAllPipesFromPipeline() {
		for(PipeContainer c : mPipeContainers) {
			removePipeFromPipeline(c);
		}
	}
	
	public void removePipeFromPipeline(PipeContainer c) {
		Pad audioResampleSrcPad = c.getAudioResample().getPad("src");
		Pad adderSinkPad = audioResampleSrcPad.getPeer();
		
		audioResampleSrcPad.setBlocked(true);
		c.getAudioResample().setState(State.NULL);
		audioResampleSrcPad.unlink(adderSinkPad);
		getAdder().releaseRequestPad(adderSinkPad);
	}
	
	public void addPipeToPipeline(PipeContainer c) throws Exception {
		Pad audioResampleSrcPad = c.getAudioResample().getPad("src");
		audioResampleSrcPad.link(createAdderSinkPad());
		c.getAudioResample().setState(getGstPipeline().getState());
	}
	
	/*
	 * Creates a new sinkpad for linking another src to the mix.
	 */
	public Pad createAdderSinkPad() throws Exception {
		Pad pad = null;
		pad = mPipe.getElementByName("mixadder").getRequestPad("sink%d");
		if(pad == null) {
			throw new Exception("FATAL ERROR: Unable to make sink pad.");
		}
		
		return pad;
	}
	
	public Element getAdder() {
		return mAdder;
	}
	
	/*
	 * decodebin, filesrc, audioconvert, and audioresample can all
	 * be accessed via the returend PipeContainer object.
	 */
	public PipeContainer createAndAddPipeToPipeline(String filePath) throws Exception {
		if(!addSampleToPipelineHasBeenCalled) {
			mPipe = Pipeline.launch("filesrc name=filesrc0 ! decodebin name=decodebin0 ! audioconvert name=audioconvert0 ! audioresample name=audioresample0 ! volume name=volume0 ! adder name=mixadder ! alsasink name=mainsink sync=false ! mixadder");
			mPipe.getElementByName("filesrc0").set("location", filePath);
			mPipe.getElementByName("mainsink").set("sync", false);
			
			mAdder = mPipe.getElementByName("mixadder");
			
			addSampleToPipelineHasBeenCalled = true;
			
			PipeContainer c = new PipeContainer();
			c.setFileSrc(mPipe.getElementByName("filesrc0"));
			c.setDecodeBin(mPipe.getElementByName("decodebin0"));
			c.setAudioConvert(mPipe.getElementByName("audioconvert0"));
			c.setAudioResample(mPipe.getElementByName("audioresample0"));
			c.setVolume(mPipe.getElementByName("volume0"));
			
			mPipeContainers.add(c);
			return c;
		}
		
		// The starting number of this is supposed to be 1.
		i++;
		
		Pad sinkpad = createAdderSinkPad();
		
		Element decodeBin = ElementFactory.make("decodebin", "decodebin" + new Integer(i).toString());
		Element audioConvert = ElementFactory.make("audioconvert", "audioconvert" + new Integer(i).toString());
		Element audioResample = ElementFactory.make("audioresample", "audioresample" + new Integer(i).toString());
		Element volume = ElementFactory.make("volume", "volume" + new Integer(i).toString());
		
		Element fileSrc = ElementFactory.make("filesrc", "filesrc" + new Integer(i).toString());
		fileSrc.set("location", filePath);
		
		PipeContainer c = new PipeContainer();
		mPipe.add(fileSrc);		
		mPipe.add(decodeBin);
		mPipe.add(audioConvert);
		mPipe.add(audioResample);
		mPipe.add(volume);
		c.setFileSrc(fileSrc);
		c.setDecodeBin(decodeBin);
		c.setAudioConvert(audioConvert);
		c.setAudioResample(audioResample);
		c.setVolume(volume);
		
		Pad sink_decodeBin = decodeBin.getPad("sink");
		Pad sink_audioConvert = audioConvert.getPad("sink");
		Pad sink_audioResample = audioResample.getPad("sink");
		Pad sink_volume = volume.getPad("sink");
		
		Pad src_fileSrc = fileSrc.getPad("src");
		Pad src_audioConvert = audioConvert.getPad("src");
		Pad src_audioResample = audioResample.getPad("src");
		Pad src_volume = volume.getPad("src");
		
		if(sink_decodeBin == null) {
			throw new Exception("decodeBin sink pad null");
		}
		
		if(sink_audioConvert == null) {
			throw new Exception("audioConvert sink pad null");
		}
		
		if(sink_audioResample == null) {
			throw new Exception("audioResample sink pad null");
		}
		
		if(sink_volume == null) {
			throw new Exception("audio sink pad null");
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
		
		if(src_volume == null) {
			throw new Exception("volume src pad null");
		}
		
		DecodeBinListener l = new DecodeBinListener(sink_audioConvert);
		decodeBin.connect(l);
		
		src_fileSrc.link(sink_decodeBin);
		// DecodeBin src is linked to audioConvert sink through callback.
		src_audioConvert.link(sink_audioResample);
		src_audioResample.link(sink_volume);
		src_volume.link(sinkpad);
		
		
		
		mPipeContainers.add(c);
		return c;
	}
}
