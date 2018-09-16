<h1> Assignment2_206 </h1>

<p>This is an university project which uses Java to process Bash inputs. <p>

<p>Bash is used to record audio and display a short 5 second video with that recorded audio.
Swing is used to create the gui. 
</p>
<h3> The home panel </h3>

<img src="https://i.imgur.com/5kKQa7x.png" height="500px" width="600px"/>
<p> This is the first thing the user sees when they run the app. The buttons are used to switch between the different <br>
panels. </p>

<h3> The creation panel </h3>
<img src="https://i.imgur.com/4MKsYe6.png" height="500px" width="600px"/>
<p> This is the creation screen. This is where the user enters a name they want for their creation. I limited the
  characters for the name so that they video output won't look weird. </p>

<h3> The viewing panel </h3>
<img src="https://i.imgur.com/J8es1Dj.png" height="500px" width="600px"/>
<p> This panel is used for viewing all the current creations. Selecting a creation will give the user
options to play or delete the creation. The stats in the textarea were just because I felt like it. </p>

<h2> How it works </h2>
<h4> Switching between panels </h4>
<p>I made use of the singleton pattern. This design pattern was applied to <em>ApplicationStates.java</em> classs. 
This class also acted as an observable. This is so that when a button is pressed the state of the application changed causing the
listeners to update. Below is some code which shows this: </p>
<p>When the state changes the observers are notified. </p>

```
	public void setState(appState state) {
		_previousState = _currentState;
		_currentState = state;
		if (_currentState != _previousState) {
			notifyObservers();
		}
	}
```

<p>Iterates through all of the observers, updating each of them. </p>

```
	public void notifyObservers() {
		List<Observer> newObserver = new ArrayList<Observer>(observers);
		for (Observer observer: newObserver) {
			observer.update();
		}
	}
```

<p>In my app, the observer is the display panels. So whenever the state changes the display changes. </p>

```
	public void update() {
		_name = panel.getText();
		getContentPane().removeAll();
		panel = panel.create(_name,_type);	
		_type = panel.getType();
		getContentPane().add(panel);
		validate();
		repaint();
		panel.playVideo();
	}
```

<h4>So, how does the application know which panel to switch to? </h4>
<p>This is where we make use of the application states. Below are the states of the application. </p>

```
	 public enum appState {
		MainMenu,Create,View,Audio,Play;
	 }
```

<p>The display panel then decides which panel is returned based on the current state. This is done through a switch/case block. </p>

```
	public DisplayPanel change() {
		switch(ApplicationStates.getInstance().getState()) {
		case MainMenu:
			_panel = new MainMenuPanel();
			break;
		case Create:
			_panel = new CreatePanel();
			_name = _panel.getText();
			break;
		case View:
			_panel = new ViewingPanel();
			_name = _panel.getText();
			_panel.setType("avi");
			break;
		case Audio:
			_panel = new RecordingPanel(_name);
			_name = _panel.getText();
			_panel.setType("wav");
			break;
		case Play:
			_panel = new VideoPanel(_name,_type);
			_name = _panel.getText();
			break;
		}
		return _panel;
	}
```

<h4> Audio and Video processing. </h4>
<p>This was done primarily using FFMPEG/Bash. Below are some examples on how Bash was implemented in my application. </p>
<p>Firstly, a class called <em>BashCommand.java</em> was created to handle commands from Bash and implement them into Java. It
checks if the command entered is correct. </p>

```
	public void command(String command) {
		try {
			ProcessBuilder builder = new ProcessBuilder("bash","-c",command);
			Process process = builder.start();
			_stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
			_stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			int exitStatus = process.waitFor();
			if(exitStatus!=0) {
				return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while processing");
			System.exit(0);
		}
	}
```

<h6>Audio recording. </h6>
<p>To record audio we can simply instantiate the BashCommand class and call the command() method. One thing to make sure of
is to record the audio in the background. This is so that the gui does not freeze. We can do this through the use of SwingWorker.</p>

```
		bash.command("ffmpeg -f alsa -ac 1 -ar 44100 -i default -t 5 ./Creations/\"" + _name +".wav\" &> /dev/null");
```

<h6> Playing the video </h6>
<p>VLCJ was used for playing the video. This is because we wanted to keep the video in the same gui as the application. We also needed to make sure that the video was also playing in the background or else the gui would freeze.</p>

```
			final EmbeddedMediaPlayer video = _component.getMediaPlayer();
			if(_name!=null){	
				video.playMedia("Creations/"+_name+ "." +_type );
			}
			return null;
		}
```
