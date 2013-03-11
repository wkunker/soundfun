package soundfun.ui;

public interface EventListener {
	/*
	 * Called by soundfun.ui when a different element
	 * has been selected. This also is called if no
	 * element was previously selected and is now selected.
	 */
	public void actionPerformed(String actionCommand);
}
