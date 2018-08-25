package name_sayer_app.tools;

import java.util.List;

import name_sayer_app.bashcmd.BashCommand;

/**
 * This class is responsible for retrieving the statistics of a creation.
 * 
 * @author bugn877
 */
public class FilesStats {
	/**
	 * This method uses Bash command to retrieve the statistics.
	 * @param name, the file name
	 * @return the string which contains the statistics.
	 */
	public String getStats(String name) {
			BashCommand bash = new BashCommand();
			bash.command("stat ./Creations/\"" + name + ".avi\" | grep +1200");
			List<String> list = bash.getList();
			//Creates the string which is set up in a nice way.
			String output = list.get(0).substring(0, list.get(0).length()-12)+"\n"
			+ list.get(1).substring(0, list.get(1).length()-12) + "\n"
			+ list.get(2).substring(0, list.get(2).length()-12);
			return output;
	}
}
