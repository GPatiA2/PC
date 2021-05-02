package ej1;

public class SyncMonitorEj11 {
	volatile int c;
	
	public SyncMonitorEj11() {
		c = 0;
	}
	
	public synchronized void inc() {
		c = c + 1;
	}
	
	public synchronized void dec() {
		c = c - 1;
	}
	
	public synchronized int getC() {
		return c;
	}
}
