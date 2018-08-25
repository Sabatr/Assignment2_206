package name_sayer_app.gui.guiPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import name_sayer_app.bashcmd.BashCommand;
import name_sayer_app.tools.ActionAdapter;
import name_sayer_app.tools.ApplicationStates;
import name_sayer_app.tools.ApplicationStates.appState;
import name_sayer_app.tools.ButtonBuilder;
import name_sayer_app.tools.FilesStats;

/**
 * This class is the panel which shows all the current creations. 
 * Allows the user to play and delete the creations.
 * 
 * @author bugn877
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class ViewingPanel extends DisplayPanel {
	private String _name;
	private String _type;
	private JList _list;
	private JTextArea _area;
	private JLabel _image;
	private BashCommand _command; 
	
	public ViewingPanel() {
		setUp();
	}
	
	@SuppressWarnings("unchecked")
	public void setUp() {
		JPanel listPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		ButtonBuilder builder = new ButtonBuilder(new Dimension(90,30));
		
		/*
		 * Setting up viewable list.
		 */
		_list = new JList();
		_list.setListData(getCreationList().toArray());
		//upon clicking a creation, the thumb nail and description changes.
		_list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
					_name=(String)_list.getSelectedValue();
					if (_name != null) {
					_area.setText("This is the creation " +_name + "\n\n\n\n"+ new FilesStats().getStats(_name));
					_image.setIcon(new ImageIcon("./Creations/PreviewIcons/" + _name + ".png"));
					}		
			}
		});
		_list.setFont(new Font("Serif",Font.PLAIN, 20));
		_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/*
		 * Setting up the scrollable list of creations.
		 */
		JScrollPane scrollList = new JScrollPane(_list);
		scrollList.setPreferredSize(new Dimension(200,700));
		listPanel.add(scrollList);
		this.add(listPanel,BorderLayout.EAST);
		
		/*
		 * Setting up the previews
		 */
		JPanel previewPanel = new JPanel();
		previewPanel.setBackground(Color.BLACK);
		previewPanel.setPreferredSize(new Dimension(700,500));
		_image = new JLabel("");
		previewPanel.add(_image);
		textPanel.add(previewPanel);
		
		/*
		 * Setting up description area
		 */
		_area = new JTextArea();
		_area.setPreferredSize(new Dimension(700,200));
		_area.setFont(new Font("Serif",Font.PLAIN, 20));
		_area.setLineWrap(true);
		_area.setEditable(false);
		textPanel.add(_area);
		
		textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
		this.add(textPanel,BorderLayout.WEST);
		
		/*
		 * Setting up buttons
		 */
		JButton playButton = builder.build("Play");
		playButton.addActionListener(new ActionAdapter() {
			public void actionPerformed(ActionEvent event) {
				if (_name != null) {
					ApplicationStates.getInstance().setState(appState.Play);
				}
			}
		});
		buttonPanel.add(playButton);
		//delete button
		JButton deleteButton = builder.build("Delete");
		deleteButton.addActionListener(new ActionAdapter() {
			public void actionPerformed(ActionEvent event) {
				if (_name != null) {
					int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+_name+"?" , null, JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						deleteCreation(_name);
						_list.setListData(getCreationList().toArray());
						_image.setIcon(null);
						_area.setText("");
					}
				}
			}
		});
		buttonPanel.add(deleteButton);
		//back button
		JButton backButton = builder.build("Back",appState.MainMenu);
		buttonPanel.add(backButton);
		listPanel.add(buttonPanel);
		listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
	}
	
	/**
	 * Returns the name of the selected line.
	 */
	@Override
	public String getText() {
		try {
			return (String)_list.getSelectedValue();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * Setting the type to be played
	 */
	public void setType(String type) {
		_type = type;
	}
	/**
	 * Getting the type to be played
	 */
	public String getType() {
		return _type;
	}
	/**
	 * @return the list of all creations
	 */
	private List<String> getCreationList() {
		_command = new BashCommand();
		_command.command("ls ./Creations | grep .avi"); 
		return _command.getList();
	}
}
