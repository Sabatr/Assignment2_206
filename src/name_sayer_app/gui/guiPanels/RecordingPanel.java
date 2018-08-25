package name_sayer_app.gui.guiPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import name_sayer_app.tools.ActionAdapter;
import name_sayer_app.tools.ApplicationStates.appState;
import name_sayer_app.tools.ButtonBuilder;
import name_sayer_app.tools.CreationMaker;

/**
 * This class is the panel which is used when the user starts recording their voice.
 * 
 * @author bugn877
 */
@SuppressWarnings("serial")
public class RecordingPanel extends DisplayPanel{
	private ButtonBuilder builder = new ButtonBuilder(new Dimension(300,50));
	private String _name;
	private String _type;
	private JLabel _label = new JLabel("");
	/**
	 * On construction, set up the recording panel.
	 */
	public RecordingPanel(String name) {
		_name = name;
		setUp();
	}
	
	/**
	 * Getter for the type.
	 */
	public void setType(String type) {
		_type = type;
	}
	/**
	 * Setter for the type.
	 */
	public String getType() {
		return _type;
	}
	/**
	 * Returns the text from the recording.
	 */
	public String getText() {
		return _name;
	}
	/**
	 * This method is used so that the creation maker can get the recording panel.
	 * @return the current recording panel.
	 */
	private RecordingPanel returnthis() {
		return this;
	}
	
	private void setUp() {
		this.add(Box.createRigidArea(new Dimension(100,300)));
		/*
		 * Setting up the label.
		 */
		_label.setText("Press the button below to start recording.");
		_label.setFont(new Font("Arial",Font.PLAIN,30));
		this.add(_label);
		
		/*
		 * Setting up the buttons.
		 */
		JPanel buttonPanel = new JPanel();
		JButton recordButton = builder.build("Record");
		recordButton.addActionListener(new ActionAdapter() {
			public void actionPerformed(ActionEvent event) {
				new CreationMaker(_name,returnthis()).execute();	
			}
		});
		buttonPanel.add(recordButton);
		
		JButton backButton = builder.build("Go back",appState.Create);
		buttonPanel.add(backButton);
		
		this.add(buttonPanel);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
}
