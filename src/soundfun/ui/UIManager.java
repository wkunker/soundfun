package soundfun.ui;

public class UIManager {
	// Singleton instance.
	private static UIManager mSingleton = null;
	//private MainWindow mMainWindow = null;
	private MainWindow mMainWindow = null;
	
	// Private to prevent multiple instances.
	private UIManager() {
		/*
		 * Set the look and feel to the native OS.
		 */
		try {
		    try {
				javax.swing.UIManager.setLookAndFeel(
						javax.swing.UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
		  System.out.println("Unable to load native look and feel");
		}
		
		mMainWindow = new MainWindow();
	}
	
	/*
	 * Returns the main JFrame instance, reloads the main window.
	 * Note: This is not a visually "seamless" reload.
	 */
	public MainWindow reloadMainWindow() {
		if(mMainWindow != null) { // Dispose old instance if it exists.
			mMainWindow = null;
		}
		mMainWindow = new MainWindow();
		return mMainWindow;
	}
	
	public MainWindow getMainWindow() {
		return mMainWindow;
	}
	
	/*
	 * Returns the only possible instance of UIManager.
	 */
	public static UIManager getSingleton() {
		if(mSingleton == null)
			mSingleton = new UIManager();
		
		return mSingleton;
	}
	
	public Label createLabel(String text) {
		return new Label(text);
	}
	
	public Label createLabel() {
		return new Label();
	}
	
	public RadioButton createRadioButton(EventListener listener) {
		return new RadioButton(listener);
	}
	
	public soundfun.ui.ButtonGroup createButtonGroup() {
		return new soundfun.ui.ButtonGroup();
	}
	
	public FileChooser createFileChooser() {
		return new FileChooser();
	}
	
	public CheckBox createCheckBox(EventListener listener) {
		return new CheckBox(listener);
	}
        
        public Dialog createDialog() {
            return new Dialog();
        }
}
