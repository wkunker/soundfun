package soundfun.util;

import java.util.ArrayList;

/*
 * @param T the event listener class.
 */
public abstract class Dispatcher<T> {
	private final ArrayList<T> mListeners = new ArrayList<T>();
	
	protected final ArrayList<T> getListeners() {
		return mListeners;
	}
	
	public void addListener(T listener) {
		mListeners.add(listener);
	}
	
	public void removeListener(T listener) {
		mListeners.remove(listener);
	}
}
