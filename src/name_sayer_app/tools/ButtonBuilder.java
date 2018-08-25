package name_sayer_app.tools;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import name_sayer_app.tools.ApplicationStates.appState;

/**
 * This class builds buttons depending on the needs of it.
 * 
 * @author bugn877
 *
 */
public class ButtonBuilder {
	private Dimension _dimension;
	public ButtonBuilder(Dimension dimension) {
		_dimension = dimension;
	}
	/**
	 * This method is used to build buttons which change view of the application.
	 * @param name, the string on the button
	 * @param state, what the view changes to when the button is pressed
	 * @return the button itself.
	 */
	public JButton build(String name, appState state) {
		JButton button = build(name);
		button.addActionListener(new ActionAdapter() {
			public void actionPerformed(ActionEvent event) {
				ApplicationStates.getInstance().setState(state);
			}
		});
		return button;
	}
	
	/**
	 * Builds a button that will have an independent functionality.
	 * @param name, the name of the button
	 * @return the button itself.
	 */
	public JButton build(String name) {
		JButton button = new JButton(name);
		button.setPreferredSize(_dimension);
		button.setFont(new Font("Serif",Font.PLAIN, _dimension.height/3));
		return button;
	}
}
