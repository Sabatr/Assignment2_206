package name_sayer_app.tools;
/**
 * Interface which contains the necessary observable methods.
 * @author bugn877
 *
 */
public interface Observable {
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();

}
