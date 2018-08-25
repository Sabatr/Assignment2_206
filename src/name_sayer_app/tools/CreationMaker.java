package name_sayer_app.tools;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import name_sayer_app.bashcmd.BashCommand;
import name_sayer_app.gui.guiPanels.RecordingPanel;
import name_sayer_app.tools.ApplicationStates.appState;

/**
 * This class creates the creation. First it records the audio,
 * and then the user is prompted if they want to keep the audio. 
 * If they keep it the creation is made, else they can redo the whole creation.
 * 
 * @author bugn877
 *
 */
public class CreationMaker extends SwingWorker<Void,Void>{
	private BashCommand bash = new BashCommand();
	private String _name;
	private RecordingPanel _recordingPanel;
	private JButton _recordButton; 
	private JButton _backButton;
	private JLabel _label;
	public CreationMaker(String name,RecordingPanel recordingPanel) {
		//The components from the recording panel are extracted so they can be updated.
		_name = name;
		_recordingPanel = recordingPanel;
		_label = (JLabel) _recordingPanel.getComponent(1);
		JPanel panel = (JPanel) _recordingPanel.getComponents()[2];
		 _recordButton = (JButton) panel.getComponent(0);
		_backButton = (JButton) panel.getComponent(1);
	}
	/**
	 * Records the audio in a background thread.
	 */
	@Override
	protected Void doInBackground() {
		_label.setText("Recording...");
		//Hides the buttons so that they cannot be pressed while recording.
		_recordButton.setVisible(false);
		_backButton.setVisible(false);
		bash.command("ffmpeg -f alsa -ac 1 -ar 44100 -i default -t 5 ./Creations/\"" + _name +".wav\" &> /dev/null");
		bash.processOutput();
		return null;
	}
	/**
	 * Once the audio has finished recording the interface is not re-enabled for the user.
	 * The user is then prompted to hear the recording again.
	 */
	protected final void done() {
		try {
			Thread.sleep(100);
			_recordButton.setVisible(true);
			_backButton.setVisible(true);
			_label.setText("Finished.");
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Error in creation process");
			System.exit(0);
		}
		playAgainPrompt();
		keepOrRedo();
	}
	
	/**
	 * This prompts the user to listen to the recording. 
	 */
	private void playAgainPrompt() {
		int reply = JOptionPane.showConfirmDialog(null, "Do you wish to listen to the recording?", "Play Again", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			ApplicationStates.getInstance().setState(appState.Play);
			playAgainPrompt();
		}
	}
	
	/**
	 * This prompts the user if they want to keep their recording.
	 * If they keep it the creation is made. Otherwise, they have an
	 * option to redo the recording or just cancel the recording all together.
	 */
	private void keepOrRedo() {
		Object[] options = {"Keep","Redo","Cancel"};
		int reply = JOptionPane.showOptionDialog(null , "Would you like to keep or redo?", "Keep or Redo",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		if (reply == JOptionPane.YES_OPTION) {
			makeCreation();       
			String command2 = "mv \"./Creations/"+ _name + ".wav\" ./Creations/SoundFiles/";
			bash.command(command2);
			ApplicationStates.getInstance().setState(appState.MainMenu);
		} else if (reply == JOptionPane.NO_OPTION){
			bash.command("rm ./Creations/\"" + _name + ".wav\"");
			_label.setText("Press the button below to start recording.");
		} else {
			bash.command("rm ./Creations/\"" + _name + ".wav\"");
			ApplicationStates.getInstance().setState(appState.MainMenu);
			
		}
	}
	/**
	 * This method uses Bash commands to create the video and merge the audio and video files together
	 * to make a creation. A thumb nail is also made upon making a creation.
	 */
	private void makeCreation() {
		bash.command("ffmpeg -f lavfi -i color=c=blue:s=600x500:d=5 "
				+ "-vf \"drawtext=fontfile=/path/to/font.ttf:fontsize=25:fontcolor=white:x=(w-text_w)/2:y=(h-text_h)/2:text='"+ _name 
				+ "'\" ./Creations/VideoFiles/\"" + _name +".mp4\"");
		bash.processOutput();
		bash.command("ffmpeg -i ./Creations/VideoFiles/\"" +_name + ".mp4\" -i ./Creations/\"" + _name
				+ ".wav\" -strict -2 -map 0:v -map 1:a ./Creations/\"" + _name + ".avi\" &> /dev/null");
		bash.processOutput();
		createThumbnail();
	}
	
	/**
	 * Creates a thumb nail that represents the video that has been made. 
	 */
	private void createThumbnail() {
		bash.command("ffmpeg -i ./Creations/\"" +_name +".avi\" -ss 00:00:03 -vframes 1 ./Creations/PreviewIcons/\""+_name+".png\"");
	}
}


