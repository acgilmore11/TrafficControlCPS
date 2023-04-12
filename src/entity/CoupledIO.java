package entity;

import java.util.LinkedList;
import java.util.Queue;

public class CoupledIO {
	private Object value;
	private boolean hasDecision = false;
	private Queue<Thread> waiting = new LinkedList<Thread>();
	
	public synchronized void setOutput(Object o) {
		this.value = o;
		this.hasDecision = true;
		notifyAll();
	}
	

	public synchronized Object waitForOutput() {
		while(!hasDecision) {
			waiting.add(Thread.currentThread());
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waiting.remove();
		}
		return this.value;
	}
	
	public void reset() {
		this.hasDecision = false;
	}
}
