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
	
	public List getList() {
		return mList;
	}
	
	public ListModel getListModel() {
		return mListModel;
	}
	
	public void addListElement(String element) {
		mListModel.addElement(element);
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
