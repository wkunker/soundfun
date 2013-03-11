package soundfun.ui;

import java.awt.event.ActionEvent;

public class EventDispatcher extends Dispatcher<EventListener> implements java.awt.event.ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
        for(EventListener curListener : this.getListeners()) {
        	curListener.actionPerformed(e.getActionCommand());
        }
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
