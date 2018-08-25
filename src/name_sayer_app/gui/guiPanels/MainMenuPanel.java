package name_sayer_app.gui.guiPanels;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import name_sayer_app.tools.ButtonBuilder;
import name_sayer_app.tools.Folder;
import name_sayer_app.tools.ApplicationStates.appState;

/**
 * This class is the first panel the user sees.
 * 
 * @author bugn877
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends DisplayPanel {
	public MainMenuPanel() {
		//When the main menu comes up, set up all the folders if they haven't been created yet.
		Folder folder = new Folder();
		folder.setUp();
		setUp();
	}

	private void setUp() {
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel imagePanel = new JPanel();
		ButtonBuilder builder = new ButtonBuilder(new Dimension(300,50));
		
		/*
		 * Add the title to the application
		 */
		JLabel label = new JLabel("Welcome to Name Sayer!");
		label.setFont(new Font("Tahoma",Font.PLAIN, 30));
		titlePanel.add(label);
		this.add(titlePanel);
		/*
		 *Adds an image icon to the display panel. 
		 */
		JLabel imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(MainMenuPanel.class.getResource("/images/face.png")));
		imagePanel.add(imageLabel);
		this.add(imagePanel);
		/*
		 * Creating the buttons for the panel.
		 */
		//For creating the creation
		JButton creationButton = builder.build("Create a creation.", appState.Create);
		buttonPanel.add(creationButton);
		//For viewing the creation
		JButton viewButton = builder.build("View Creations", appState.View);
		buttonPanel.add(viewButton);
		//Adding the panel to the main panel.
		this.add(buttonPanel);

		/*
		 * A layout going from the top down.
		 */
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
}
