package newsfeed;

import java.util.ArrayList;


public abstract class Observable  {
	
	private ArrayList<Observer> observerList ;
	
	public Observable() {
		
		observerList = new ArrayList<Observer>();
	}
	
	public void addObserver(Observer o) {
		
		observerList.add(o);
	}
	public void removeObserver(Observer o) {
		
		observerList.remove(o);	
	}
	
	public void notifyObserver (String s) {
		for (Observer observer : observerList) {
			observer.update(s);
		}
		
	}
	public ArrayList<Observer> getObserver () {
		return observerList ;
	}
		
}
