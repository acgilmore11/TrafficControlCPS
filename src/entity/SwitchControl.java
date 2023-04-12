package entity;

import java.util.Queue;
import java.util.LinkedList;

public class SwitchControl {
	private boolean hasDecision = false;
	private boolean lightSwitch = false;
	private Queue<Thread> waiting = new LinkedList<Thread>();
	
	public synchronized void setSwitch(boolean s) {
		this.hasDecision = true;
		this.lightSwitch = s;
		notifyAll();
	}
	
	public synchronized boolean waitForSwitch() throws InterruptedException {
		while(!hasDecision) {
			waiting.add(Thread.currentThread());
			wait();
			waiting.remove();
		}
		return this.lightSwitch;
	}
	
	public void reset() {
		this.hasDecision = false;
	}

}
