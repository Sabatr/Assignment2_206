package name_sayer_app.gui.guiPanels;

import javax.swing.JPanel;

import name_sayer_app.bashcmd.BashCommand;

/**
 * This class provides a base for the children panels.
 * 
 * @author bugn877
 */
@SuppressWarnings("serial")
public abstract class ParentPanel extends JPanel {
	/**
	 * Deletes a creation and all of the files corresponding it.
	 * @param name
	 */
	public void deleteCreation(String name) {
		BashCommand bash = new BashCommand();
		bash.command("rm ./Creations/\"" + name + ".avi\"");
		bash.command("rm ./Creations/SoundFiles/\"" + name + ".wav\"");
		bash.command("rm ./Creations/VideoFiles/\"" + name + ".mp4\"");	
		bash.command("rm ./Creations/PreviewIcons/\"" + name + ".png\"");
	}
	
	/**
	 * Hook methods. Meant to be overriden when the methods are used.
	 */
	public void playVideo() {}
	public abstract DisplayPanel create();
	public abstract DisplayPanel create(String name,String type);
	public String getType() {return "";}
	public String getText() {return "";}
}
