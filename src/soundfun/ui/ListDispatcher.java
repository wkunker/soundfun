package soundfun.ui;

/*
 * Used internally by the soundfun.ui package.
 */
class ListDispatcher extends Dispatcher<ListListener> implements javax.swing.event.ListSelectionListener {

	@Override
	public void valueChanged(javax.swing.event.ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
	        List l = (List)e.getSource();
	        for(ListListener curListener : this.getListeners()) {
	        	curListener.selected((String)l.getSelectedValue());
	        }
		}
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
