package name_sayer_app.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * This singleton determines whether or not the display panel
 * should be changed upon a button being pressed.
 * 
 * @author bugn877
 *
 */
public class ApplicationStates implements Observable{
	private List<Observer> observers = new ArrayList<Observer>();
	/**
	 * Prevents other classes instantiating this class.
	 */
	private ApplicationStates() {
	}
	
	/**
	 * Representatives of the states of the application. 
	 */
	 public enum appState {
		MainMenu,Create,View,Audio,Play;
	}
	private static ApplicationStates _applicationStates = new ApplicationStates();
	private appState _currentState = appState.MainMenu;
	private appState _previousState = appState.MainMenu;
	
	/**
	 * @return the current state of the application.
	 * This is done to check if the application needs to change or not.
	 */
	public appState getState() {
		return _currentState;
	}
	
	/**
	 * @return the previous state of the application
	 */
	public appState getPrevState() {
		return _previousState;
	}
	
	/**
	 * Upon setting state, check if the button pressed changes the state. If it does, change the
	 * look of the application.
	 * @param state
	 */
	public void setState(appState state) {
		_previousState = _currentState;
		_currentState = state;
		if (_currentState != _previousState) {
			//observers are updated when the state changes.
			notifyObservers();
		}
	}
	
	/**
	 * @return the singleton instance
	 */
	public static ApplicationStates getInstance() {
		return _applicationStates;
	}
	
	/**
	 * Adds the observer to the list
	 */
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	/**
	 * Removes the observer from the list
	 */
	
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	/**
	 * Notifies the observer to update. This causes the change of the application.
	 */
	public void notifyObservers() {
		List<Observer> newObserver = new ArrayList<Observer>(observers);
		for (Observer observer: newObserver) {
			observer.update();
		}
	}
}
