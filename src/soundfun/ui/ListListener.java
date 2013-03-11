package soundfun.ui;

/*
 * Library independent interface for listening to events from a List box.
 */
public interface ListListener {
	/*
	 * Called by soundfun.ui when a different element
	 * has been selected. This also is called if no
	 * element was previously selected and is now selected.
	 */
	public void selected(String action);
}
