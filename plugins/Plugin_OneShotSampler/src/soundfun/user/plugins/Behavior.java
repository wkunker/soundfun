package soundfun.user.plugins;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.GstObject;
import org.gstreamer.Message;
import org.gstreamer.State;
import org.gstreamer.TagList;

import soundfun.plugins.Action;
import soundfun.sound.BusListener;
import soundfun.sound.Player;
import soundfun.sound.Recorder;
import soundfun.sound.SoundManager;
import soundfun.ui.CheckBox;
import soundfun.ui.FileChooser;
import soundfun.ui.Label;
import soundfun.ui.RadioButton;
import soundfun.ui.ToggleButton;
import soundfun.ui.UIManager;
import soundfun.util.Log;
import soundfun.ui.ButtonGroup;

/*
 * A plugin can often be written just by modifying this class.
 * This controls the overall behavior of the plugin.
 * 
 * For safety reasons, this class should not be extended.
 */
public final class Behavior implements Action, BusListener {
	private DataContainer mDataContainer = null;
	private Label mChosenSampleFile = null;
	private Player mPlayer = null;
	private Label mPlayBinDuration = null;
	
	soundfun.ui.Button button_chooseSampleFile = null;
	ToggleButton recordButton = null;
	String currentMode = null;
	boolean recorderArmed = false;
	private Recorder recordingPipeline;
	private String recordingFilename = null;
	CheckBox checkbox_loopAtEos = null;
	private boolean bLoopAtEos;
	String releaseMode = null;
	
	public Behavior(soundfun.plugins.PluginInfo pluginInfo, DataContainer dataContainer) {
            mDataContainer = dataContainer;

            recorderArmed = false;

            pluginInfo.setTitle("One Shot Sampler");
            pluginInfo.setPanel(mDataContainer.getPanel());
            
            /*
             * NO CODE SHOULD BE PLACED BELOW THIS POINT IN THE CONSTRUCTOR.
             */
            try {
                this.initialize();
            } catch (Exception ex) {
                Logger.getLogger(Behavior.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
        /*
         * This is where the plugin should be constructed.
         * This won't get called until everything else in the constructor
         * is finished.
         */
        public void initialize() throws Exception {
            // Main button press listener.
            ButtonPressListener buttonListener = new ButtonPressListener();

            /********** General UI ********/
            // Description label... Basic instructions for end-user.
            Label label_description = UIManager.getSingleton().createLabel();
            label_description.setText("Simple One-shot sampler. Press and hold button to play, release to stop.");
            //mDataContainer.getPanel().add(label_description, "wrap, span 2");

            // Current clip duration. Invisible until something actually
            long duration = 0;
            mPlayBinDuration = UIManager.getSingleton().createLabel("Recording duration: " + new Long(duration).toString());
            //mDataContainer.getPanel().add(mPlayBinDuration);
            mPlayBinDuration.setVisible(false);

            // Description
            mDataContainer.getPanel().add(label_description, "spanx 4, wrap");

            // Radio button to choose loading of files.
            ButtonGroup mainFunctions = UIManager.getSingleton().createButtonGroup();
            RadioButton radioMp3 = UIManager.getSingleton().createRadioButton(buttonListener);
            radioMp3.setText("MP3 File");
            mDataContainer.getPanel().add(radioMp3);
            mainFunctions.add(radioMp3);
            radioMp3.setActionCommand("radioMp3Selected");

            // Radio button to choose recording from autoaudiosrc or similar.
            RadioButton radioRecord = UIManager.getSingleton().createRadioButton(buttonListener);
            radioRecord.setText("Record from default microphone");
            mDataContainer.getPanel().add(radioRecord, "wrap");
            mainFunctions.add(radioRecord);
            radioRecord.setActionCommand("radioRecordSelected");

            /********************* MP3 UI ********************/
                    mChosenSampleFile = UIManager.getSingleton().createLabel();
            mChosenSampleFile.setText("Currently loaded mp3: none");
            mDataContainer.getPanel().add(mChosenSampleFile, "spanx 2");

                    // Button to choose the audio sample.
            button_chooseSampleFile = new soundfun.ui.Button(buttonListener);
            button_chooseSampleFile.setText("Choose sample...");
            button_chooseSampleFile.setActionCommand("chooseSampleFile");
            mDataContainer.getPanel().add(button_chooseSampleFile);

            /********************* Record from audio UI ********************/
            recordButton = new ToggleButton(buttonListener);
            recordButton.setText("Record");
            mDataContainer.getPanel().add(recordButton, "wrap");
            recordButton.setActionCommand("recordButton");


            /******************** Options for when button is released ***********************/
            ButtonGroup buttonReleaseOptions = UIManager.getSingleton().createButtonGroup();

            RadioButton radioButton_startOverOnRelease = UIManager.getSingleton().createRadioButton(buttonListener);
            radioButton_startOverOnRelease.setText("Start over on release");
            mDataContainer.getPanel().add(radioButton_startOverOnRelease);
            buttonReleaseOptions.add(radioButton_startOverOnRelease);
            radioButton_startOverOnRelease.setActionCommand("radioButton_startOverOnRelease");
            radioButton_startOverOnRelease.setSelected(true);
            releaseMode = "radioButton_startOverOnRelease";

            RadioButton radioButton_pauseOnRelease = UIManager.getSingleton().createRadioButton(buttonListener);
            radioButton_pauseOnRelease.setText("Pause on release");
            mDataContainer.getPanel().add(radioButton_pauseOnRelease);
            buttonReleaseOptions.add(radioButton_pauseOnRelease);
            radioButton_pauseOnRelease.setActionCommand("radioButton_pauseOnRelease");

            RadioButton radioButton_muteOnRelease = UIManager.getSingleton().createRadioButton(buttonListener);
            radioButton_muteOnRelease.setText("Mute on release");
            mDataContainer.getPanel().add(radioButton_muteOnRelease, "wrap");
            buttonReleaseOptions.add(radioButton_muteOnRelease);
            radioButton_muteOnRelease.setActionCommand("radioButton_muteOnRelease");


            /********************* Loop at end of stream checkbox ****************************/
            // The label
            Label label_loopAtEOS = UIManager.getSingleton().createLabel("Loop at end of sample");
            mDataContainer.getPanel().add(label_loopAtEOS);

            // The checkbox
            checkbox_loopAtEos = UIManager.getSingleton().createCheckBox(buttonListener);
            mDataContainer.getPanel().add(checkbox_loopAtEos);
            checkbox_loopAtEos.setActionCommand("loopAtEos");


            /*************** Set the default state of everything in the plugin *************/
            mChosenSampleFile.setEnabled(false);
            button_chooseSampleFile.setEnabled(false);
            recordButton.setEnabled(false);
            currentMode = "";
            
            /**************** Register ****************/
            buttonListener.registerBehavior(this);
        }
        
	/*
	 * (non-Javadoc)
	 * @see soundfun.plugins.Action#serialEvent(java.lang.String)
	 * 
	 * Normally called when 
	 */
	@Override
	public void serialEvent(String evt) {
		soundfun.util.Log.logDbgMsg(this, "serialEvent(evt) start... evt: " + evt);
		if(evt.equals("buttonPressed")) {
			/*****************
			 * Button pressed
			 ****************/
			
			if(recorderArmed) {
				try {
					// Start recording.
					recordingPipeline.start();
				} catch(Exception e) {
					// Must be a bug in the code (this or gstreamer).
					e.printStackTrace();
				}
			} else {
				// If the pipeline exists, try to play the PlayBin.
				if(mPlayer != null) {
					mPlayer.setMute(false);
					mPlayer.start();
				}
			}
		} else if(evt.equals("buttonReleased")) {
			/****************
			 * Button released
			 ****************/
			
			if(mPlayer != null) {
				if(releaseMode.equals("radioButton_startOverOnRelease")) {
					mPlayer.stop();
				} else if(releaseMode.equals("radioButton_pauseOnRelease")) {
					mPlayer.pause();
				} else if(releaseMode.equals("radioButton_muteOnRelease")) {
					try {
						mPlayer.setMute(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Log.logErrMsg("OneShotSampler: releaseMode \"" + releaseMode + "\" wasn't found when trying to release.");
				}
			}
			
			if(recorderArmed) {
				try {
					// At this point the file writes out the clip, and the recorder is no longer armed.
					recordingPipeline.stop();
				} catch(Exception e) {
					// Must be a bug in the code (this or gstreamer).
					e.printStackTrace();
				}
				recorderArmed = false;
				recordButton.setSelected(false);
				
				// Now that the file has been made, load it.
				mPlayer = SoundManager.getSingleton().createPlayer(recordingFilename);
            	mPlayer.addBusListener(this);
				mPlayer.stop();
				
				// TODO
				// Here it will be necessary to "hack" gstreamer into
				// spitting out the duration, since it doesn't seem
				// to do so until play is called.
				// Maybe someone with more gstreamer experience can
				// fix this properly eventually.
			}
		}
	}
	
	/*
	 * Called when the button being listened to 
	 */
	public void actionPerformed(String actionCommand) {
		
		if(actionCommand.equals("chooseSampleFile")) {
			
			/*************************************************************
			 * A sample has been chosen. Load the resources and set it up.
			 *************************************************************/
			
	        FileChooser fc = UIManager.getSingleton().createFileChooser();
	        int returnVal = fc.showOpenDialog(mDataContainer.getPanel());
	
	        if (returnVal == FileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            
	            try {
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            	
	            mChosenSampleFile.setText("Currently loaded sample: " + file.getAbsolutePath());
	            soundfun.util.Log.logDbgMsg(this, "Currently loaded sample: " + file.getAbsolutePath());
	            
	            try {
	            	/*
	            	 * If there was previously a PlayBin, it has to be stopped first.
	            	 * 
	            	 * TODO Make sure previous PlayBin is released from memory.
	            	 */
	            	if(mPlayer != null) {
	            		mPlayer.dispose();
	            		mPlayer = null;
	            	}
	            	
	            	mPlayer = SoundManager.getSingleton().createPlayer(file.getAbsolutePath());
	            	mPlayer.addBusListener(this);
					
	            	mPlayer.stop();
	            	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
        } else if(actionCommand.equals("recordButton")) {
            try {
        	if(recordButton.isSelected() && !recorderArmed) {
        		/* 
            	 * When the record button is pushed, it's simply armed.
            	 * When the record button is armed, pushing the corresponding
            	 * button on the physical box will cause the record sequence to
            	 * activate. Releasing it will finish the recording.
            	 */
            	recordingFilename = new Long(soundfun.util.Globals.autoIncrement()).toString() + ".mp3";
            	recordingPipeline = SoundManager.getSingleton().createRecorder(recordingFilename);
            	recorderArmed = true;
            	recordingPipeline.addBusListener(this);
        	} else if (!recordButton.isSelected()) {
        		if(recorderArmed) {
        			/*
        			 * Disarm the recorder.
        			 */
        			recordingPipeline.stop();
        			recordingPipeline.removeBusListener(this);
        			recordingPipeline.dispose();
            		recordingPipeline = null;
        		}
        		recorderArmed = false;
        	}
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if(actionCommand.equals("radioMp3Selected")) {
        	mChosenSampleFile.setEnabled(true);
        	button_chooseSampleFile.setEnabled(true);
        	recordButton.setEnabled(false);
        	currentMode = "mp3";
        	
        	// TODO
        	
        	
        	if(mPlayer != null) {
        		mPlayer.dispose();
        		mPlayer = null;
        		recordingPipeline.removeBusListener(this);
        		recordingPipeline.dispose();
        		recordingPipeline = null;
        	}
        } else if(actionCommand.equals("radioRecordSelected")) {
        	mChosenSampleFile.setEnabled(false);
        	button_chooseSampleFile.setEnabled(false);
        	recordButton.setEnabled(true);
        	currentMode = "record";
        	
        	if(mPlayer != null) {
        		mPlayer.dispose();
        		mPlayer = null;
        	}
        	
        	mChosenSampleFile.setText("Currently loaded mp3: none");
        } else if(actionCommand.equals("loopAtEos")) {
        	if(checkbox_loopAtEos.isSelected()) {
        		bLoopAtEos = true;
        	} else {
        		bLoopAtEos = false;
        	}
        } else if(actionCommand.equals("radioButton_startOverOnRelease")) {
        	releaseMode = "radioButton_startOverOnRelease";
        	Log.logDbgMsg(this, "radioButton_startOverOnRelease");
        } else if(actionCommand.equals("radioButton_pauseOnRelease")) {
        	releaseMode = "radioButton_pauseOnRelease";
        	Log.logDbgMsg(this, "radioButton_pauseOnRelease");
        } else if(actionCommand.equals("radioButton_muteOnRelease")) {
        	releaseMode = "radioButton_muteOnRelease";
        	Log.logDbgMsg(this, "radioButton_muteOnRelease");
        }
	}
	
	@Override
	public void durationChanged(GstObject arg0, Format arg1, long arg2) {
		Log.logDbgMsg(this, "durationChanged " + new Long(arg2).toString());
		
		if(mPlayer.getGstPipeline().equals(arg0.getParent().getParent().getParent().getParent())) {
			// This tells which GStreamer object had the duration change.
		}
	}

	@Override
	public void endOfStream(GstObject arg0) {
		Log.logDbgMsg(this, "endOfStream... objectName: " + arg0.getName());
		
		// Loop the stream, if it's enabled.
		if(bLoopAtEos)
			mPlayer.seekToBeginning();
	}

	@Override
	public void segmentStart(GstObject source, Format format, long position) {
		Log.logDbgMsg(this, "segmentStart position: " + new Long(position).toString());
	}

	@Override
	public void asyncDone(GstObject source) {
		Log.logDbgMsg(this, "asyncDone... sourceName: " + source.getName());
	}

	@Override
	public void segmentDone(GstObject source, Format format, long position) {
		Log.logDbgMsg(this, "segmentDone... position: " + new Long(position).toString());
	}

	@Override
	public void errorMessage(GstObject source, int code, String message) {
		Log.logDbgMsg(this, "errorMessage: " + message);
	}

	@Override
	public void infoMessage(GstObject source, int code, String message) {
		Log.logDbgMsg(this, "infoMessage (sourceName: " + source.getName() + "): " + message);
	}

	@Override
	public void busMessage(Bus bus, Message message) {
		Log.logDbgMsg(this, "busMessage:" + message);
	}

	@Override
	public void bufferingData(GstObject source, int percent) {
		Log.logDbgMsg(this, "bufferingData: " + new Integer(percent).toString() + "%");
	}

	@Override
	public void stateChanged(GstObject source, State current) {
		Log.logDbgMsg(this, "stateChanged: (sourceName: " + source.getName() + ")" + current.toString());
	}

	@Override
	public void tagsFound(GstObject source, TagList tagList) {
		Log.logDbgMsg(this, "tagsFound");
	}

	@Override
	public void warningMessage(GstObject source, int code, String message) {
		Log.logDbgMsg(this, "warningMessage: " + message);
	}
}
