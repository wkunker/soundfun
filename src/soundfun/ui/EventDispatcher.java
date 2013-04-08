package soundfun.ui;

import java.awt.event.ActionEvent;
import soundfun.util.Dispatcher;
import soundfun.util.Log;

public class EventDispatcher extends Dispatcher<EventListener> implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Log.logDbgMsg(this, "UI event occured (" + e.getClass().getName() + ")");
        for(EventListener curListener : this.getListeners()) {
                curListener.actionPerformed(e.getActionCommand());
        }
    }
}
