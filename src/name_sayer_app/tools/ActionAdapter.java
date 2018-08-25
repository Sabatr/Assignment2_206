package name_sayer_app.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class is particularly useful as it allows multiple classes to create 
 * this class to get the actionPerformed method rather than having all of them 
 * implement ActionListener.
 * @author bugn877
 *
 */
public class ActionAdapter implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
