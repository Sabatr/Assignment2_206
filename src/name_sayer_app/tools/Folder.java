package name_sayer_app.tools;

import name_sayer_app.bashcmd.BashCommand;
/**
 * This class is used to manage folders.
 * @author bugn877
 *
 */
public class Folder {
	/**
	 * Creates directories. If the directories already exists then nothing is done.
	 */
	public void setUp() {
		try {
			BashCommand command = new BashCommand();
			command.command("mkdir Creations");
			command.command("mkdir ./Creations/SoundFiles");
			command.command("mkdir ./Creations/VideoFiles");
			command.command("mkdir ./Creations/PreviewIcons");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
