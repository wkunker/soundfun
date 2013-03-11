package soundfun.ui;

class ListTimerListener implements java.awt.event.ActionListener {
	ListPanel mListPanel = null;
	String mElement = null;
	
	ListTimerListener(ListPanel p, String el) {
		mListPanel = p;
		mElement = el;
	}
	
	@Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        mListPanel._timerCalled(mElement);
    }
}
