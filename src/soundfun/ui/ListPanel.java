package soundfun.ui;

/*
 * A panel which is specifically designed to hold a functioning list box within.
 */
public class ListPanel extends Panel {
	private static final long serialVersionUID = soundfun.util.Globals.API_VERSION;
	
	private final ListDispatcher mListDispatcher =
			new ListDispatcher();
	
	private final ListModel mListModel =
			new ListModel();
	
	private List mList =
			new List();
	
	public ListPanel() {
		mList.addListSelectionListener(mListDispatcher);
	}
	
	void _timerCalled(String element) {
		mListModel.addElement(element);
	}
	
	public List getList() {
		return mList;
	}
	
	public ListModel getListModel() {
		return mListModel;
	}
	
	public void addListElement(String element) {
		// The process of adding elements to the list panel is
		// executed its own "Swing-friendly" thread so no
		// anomalous behavior occurs.
		ListTimerListener lt = new ListTimerListener(this, element);
		javax.swing.Timer t = new javax.swing.Timer(1, lt);
        t.setRepeats(false);
        t.start();
	}
	
	public void removeListElement(String element) {
		mListModel.removeElement(element);
	}
	
	public void addListListener(ListListener listener) {
    	mListDispatcher.addListener(listener);
    }
    
    public void removeListListener(ListListener listener) {
    	mListDispatcher.removeListener(listener);
    }
}
