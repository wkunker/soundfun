package soundfun.ui;

public class UIManager {
	// Singleton instance.
	private static UIManager mSingleton = null;
	//private MainWindow mMainWindow = null;
	private MainWindow mMainWindow = null;
	
	// Private to prevent multiple instances.
	private UIManager() {
		mMainWindow = new MainWindow();
	}
	
	/*
	 * Returns the main JFrame instance, reloads the main window.
	 * Note: This is not a visually "seamless" reload.
	 * @param panel the javax.swing.JPanel to be loaded in.
	 */
	public MainWindow reloadMainWindow() {
		/*
		 * TODO: ActionDetailsPanel currently must be injected into the
		 * constructor of the main window class. This is a huge
		 * limitation because the whole window has to be reloaded
		 * when a different plugin is selected for viewing. It's
		 * done like this for now to make NetBeans prototyping easy,
		 * but this will have to change in the future, in favor
		 * of a more permanent solution (ie inject through a method).
		 */
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
}
