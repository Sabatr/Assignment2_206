package name_sayer_app.gui;


import javax.swing.JFrame;

import name_sayer_app.gui.guiPanels.DisplayPanel;
import name_sayer_app.gui.guiPanels.ParentPanel;
import name_sayer_app.tools.Observable;
import name_sayer_app.tools.Observer;

/**
 * This class handles the update of the panels when different buttons are pressed
 * 
 * @author bugn877
 */
@SuppressWarnings("serial")
public class Display extends JFrame implements Observer {
	private Observable observable;
	private String _name;
	private String _type;
	private ParentPanel panel = new DisplayPanel();
	
	public Display(Observable observable) {
		this.createDisplay();
		this.observable = observable;
		this.observable.addObserver(this);
	}
	/**
	 * Creating the frame of the application.
	 */
	private void createDisplay() {
		//initially set up the panel
		panel = new DisplayPanel().create();
		getContentPane().add(panel);
		//set up JFrame with necessary properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Name Sayer");
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * This method gets called the state of the application changes.
	 * Allows the application to switch screens.
	 * Replaces the current panel with a new one.
	 */
	public void update() {
		//allows names to be passed through to different panels.
		_name = panel.getText();
		getContentPane().removeAll();
		panel = panel.create(_name,_type);	
		//sends through the video type, .wav or .avi
		_type = panel.getType();
		//The display panel determines which panel is actually displayed.
		getContentPane().add(panel);
		validate();
		repaint();
		//plays a video if the panel allows it.
		panel.playVideo();
	}
}
