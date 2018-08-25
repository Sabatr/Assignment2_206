package name_sayer_app.bashcmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class handles the bash commands and the processes with it.
 * 
 * @author bugn877
 */
public class BashCommand {
	private BufferedReader _stdOut;
	private BufferedReader _stdErr;
	
	/**
	 * Runs the commands that is inputed
	 * @param command, a string of that bash command
	 */
	public void command(String command) {
		try {
			ProcessBuilder builder = new ProcessBuilder("bash","-c",command);
			Process process = builder.start();
			//reading the standard output
			_stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
			//reading the standard error
			_stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			//gets the exit value of the process. The process is terminated when the command is finished processing.
			int exitStatus = process.waitFor();
			if(exitStatus!=0) {
				return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while processing");
			System.exit(0);
		}
	}
	/**
	 * Processes the standard output and standard error.
	 */
	public void processOutput() {
		//String line = null;
		try {
			while((_stdOut.readLine()) != null) {}
			_stdOut.close();
			while((_stdErr.readLine())!= null) {}
			_stdErr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Only gets called after a command has been entered.
	 * @return a list of the creations already made. 
	 */
	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		try {
			String line = null;
			while ((line = _stdOut.readLine()) != null ) {
				//sets the string to remove the ".avi"
				line = line.substring(0, line.length()-4);
				list.add(line);
			}
			_stdOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}	
}
