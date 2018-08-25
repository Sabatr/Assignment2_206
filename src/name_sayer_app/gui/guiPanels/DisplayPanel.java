package name_sayer_app.gui.guiPanels;

import name_sayer_app.tools.ApplicationStates;

/**
 * This class decides which of the panels to display upon updating.
 * 
 * @author bugn877
 */
@SuppressWarnings("serial")
public class DisplayPanel extends ParentPanel {
	private DisplayPanel _panel;
	private String _name;
	private String _type;
	
	/**
	 * These methods create the actual panel to display.
	 */
	public DisplayPanel create(String name,String type) {
		this._name = name;
		this._type = type;
		return change();
	}
	/**
	 * This method is only used when the application is first opened.
	 */
	public DisplayPanel create() {
		return change();
	}
	/**
	 * @return the display panel which contains the attributes of the wanted panel.
	 */
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
	
	/**
	 * Hook methods.
	 */
	public String getText() {
		return super.getText();
	}	
	public String getType() {
			return super.getType();	
	}
	public void setType(String string) {}
	public void playVideo() {}
}
