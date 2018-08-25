package name_sayer_app.gui.guiPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingWorker;

import name_sayer_app.tools.ApplicationStates;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * This class is the panel which displays the video.
 * This uses the vlcj library to display the video within the application.
 * 
 * @author bugn877
 *
 */
@SuppressWarnings("serial")
public class VideoPanel extends DisplayPanel {
	private String _name;
	private  EmbeddedMediaPlayerComponent _component;
	private String _type;
	
	public VideoPanel(String name,String type) {
		setUp();
		_component = new EmbeddedMediaPlayerComponent() {
			//Upon finishing playing the video, these methods are called.
			public void finished(MediaPlayer player) {
				player.stop();
				removeAll();
				validate();
				repaint();
			}
		};
		_name = name;
		_type = type;
		this.add(_component);
	}
	
	public void setUp() {
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(1000,800));
		this.setLayout(new BorderLayout(0,0));
	}
	
	public String getText() {
		return _name;
	}
	/**
	 * Plays the video on the screen.
	 */
	public void playVideo() {
		try {
			new VideoPlayer(_component,_name,_type).execute();
			Thread.sleep(6000);
			//switch back to the screen before playing the video.
			ApplicationStates.getInstance().setState(ApplicationStates.getInstance().getPrevState());	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * This class is used to make sure the gui doesn't freeze when the video is playing.
	 */
	private class VideoPlayer extends SwingWorker<Void,Void> {
		private EmbeddedMediaPlayerComponent _component;
		private String _name;
		private String _type;
		public VideoPlayer(EmbeddedMediaPlayerComponent component,String name,String videoType) {
			_component = component;
			_name = name;
			_type = videoType;
		}
		/**
		 * Runs the video player in a background thread.
		 */
		protected Void doInBackground() throws Exception {
			final EmbeddedMediaPlayer video = _component.getMediaPlayer();
			if(_name!=null){	
				video.playMedia("Creations/"+_name+ "." +_type );
			}
			return null;
		}
	}
}
