package name_sayer_app.tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class sets a limit to the number of characters allows to be entered in the JTextField.
 * 
 * @author bugn877
 *
 */
@SuppressWarnings("serial")
public class JTextFieldLimit extends PlainDocument {
	private int _limit;
	public JTextFieldLimit(int limit) {
		_limit = limit;
	}
	/**
	 * Checks if the word entered is greater the limit. It doesn't let the user
	 * enter more characters once they reach the limit.
	 */
	public void insertString(int offset, String word,AttributeSet set) throws BadLocationException{
		if (word == null) {
			return;
		}
		if (getLength() + word.length()<=_limit) {
			super.insertString(offset, word, set);
		}
	}
}
