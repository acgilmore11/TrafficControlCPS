package entity;

public class ComponentThread extends Thread{
	private Component component;
	
	public ComponentThread(Component c) {
		this.component = c;
	}
	
	public void run() {
		component.react();
	}

}
