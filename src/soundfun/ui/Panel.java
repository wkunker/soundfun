package soundfun.ui;

import java.awt.LayoutManager;

/*
 * Represents a generic section of a window.
 */
public class Panel extends javax.swing.JPanel {
	private static final long serialVersionUID = soundfun.util.Globals.API_VERSION;
	
	public Panel() {
		super();
	}

	public Panel(LayoutManager layout) {
		super(layout);
	}
}

