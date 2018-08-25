package name_sayer_app.gui.guiPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import name_sayer_app.bashcmd.BashCommand;
import name_sayer_app.tools.ActionAdapter;
import name_sayer_app.tools.ApplicationStates;
import name_sayer_app.tools.ButtonBuilder;
import name_sayer_app.tools.JTextFieldLimit;
import name_sayer_app.tools.ApplicationStates.appState;

/**
 * This class is a panel of the creation menu. 
 * 
 * @author bugn877
 */
@SuppressWarnings("serial")
public class CreatePanel extends DisplayPanel {
	private JTextField _nameField;
	/**
	 * private enum which represents if the outcome of the user input.
	 */
	private enum buttonState {
		empty,exists,create,invalid,spaceOnly;
	}
	
	public CreatePanel() {
		setUp();
	}

	private void setUp() {
		JPanel buttonPanel = new JPanel();
		JPanel inputPanel = new JPanel();

		ButtonBuilder builder = new ButtonBuilder(new Dimension(300,50));
		
		/*
		 * Adds instructions to the user. Setting up the labels.
		 */
		JLabel label = new JLabel("Please enter your full name.(Note:Maximum 35 characters.)",SwingConstants.CENTER);
		label.setFont(new Font("Tahoma",Font.PLAIN, 20));
		inputPanel.add(label);
		/*
		 * Creates a Jtextfield for the user to type down their creations.
		 */
		_nameField = new JTextField();
		 _nameField.setDocument(new JTextFieldLimit(35));
		//_nameField.setPreferredSize(new Dimension(400,40));
		_nameField.setFont(new Font("Courier",Font.PLAIN,40));
		inputPanel.add(_nameField);
		inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
		
		/*
		 * Sets up the buttons
		 */
		JButton createButton = builder.build("Create");
		createButton.addActionListener(new ActionAdapter() {
			public void actionPerformed(ActionEvent event) {
				switch(checkEnteredDetails(_nameField.getText())) {
				case empty:
					JOptionPane.showMessageDialog(buttonPanel,"Error: Empty textbox.");
					break;
				case exists:
					int reply = JOptionPane.showConfirmDialog(null, "Creation already exists. "
							+ "Do you wish to overwrite?\nNote: Overriding will delete the current version of "
							+ "the creation", null, JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.NO_OPTION) {
						_nameField.setText("");
					} else {
						deleteCreation(_nameField.getText());
						ApplicationStates.getInstance().setState(ApplicationStates.appState.Audio);
					}
					break;
				case invalid:
					JOptionPane.showMessageDialog(buttonPanel,"Error: Invalid characters.");
					_nameField.setText("");
					break;
				case create:
					ApplicationStates.getInstance().setState(ApplicationStates.appState.Audio);
					break;
				case spaceOnly:
					JOptionPane.showMessageDialog(buttonPanel,"Error: This name only contains spaces.");
					break;
				default:
					break;
				}
			}
		});
		buttonPanel.add(createButton);
			
		JButton backButton = builder.build("Back", appState.MainMenu);
		buttonPanel.add(backButton);
		
		/*
		 * Adds the components to the final panel. 
		 */
		JPanel test = new JPanel();
		test.add(Box.createRigidArea(new Dimension(200,200)));
		test.add(inputPanel);
		test.add(Box.createRigidArea(new Dimension(50,50)));
		test.add(buttonPanel);
		test.setLayout(new BoxLayout(test,BoxLayout.Y_AXIS));
		this.add(test);
	}
	
	/**
	 * Checks if the name put in the textfield is valid.
	 */
	private buttonState checkEnteredDetails(String fullname) {
		if (fullname.isEmpty()) {
			return buttonState.empty;
		} else if (exists(fullname)) {
			return buttonState.exists;
		} else if (containsIllegals(fullname)) {
			return buttonState.invalid;
		} else if (justSpace(fullname)) {
			return buttonState.spaceOnly;
		}
		return buttonState.create;
	}
	
	/**
	 * This method compares the name to the legal characters regex.
	 */
	private boolean containsIllegals(String toExamine) {
		String regex = "[a-zA-Z0-9_ -]+";
		return !(toExamine.matches(regex));

	}
	
	/**
	 * Checks if the creation already exists.
	 */
	private boolean exists(String toExamine) {
		BashCommand bash = new BashCommand();
		bash.command("ls ./Creations");
		List<String> creationList = bash.getList();
		if (creationList.contains(toExamine)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the entered name is just a space.
	 */
	private boolean justSpace(String toExamine) {
		return (toExamine.trim().equals(""));
	}

	/**
	 * Overrides getText.
	 */
	public String getText() {
		return _nameField.getText();
	}
}
