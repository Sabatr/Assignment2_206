package name_sayer_app;

import javax.swing.SwingUtilities;

import name_sayer_app.gui.Display;
import name_sayer_app.tools.ApplicationStates;

/**
 * This is where the program is ran. 
 * 
 * @author bugn877
 */
public class Main {
	public Main() {
		new Display(ApplicationStates.getInstance());
	}
	public static void main(String[] args) {
		//Makes sure that this program is initially ran on the event dispatch thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
}
