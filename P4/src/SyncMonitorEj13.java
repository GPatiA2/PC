
public class SyncMonitorEj13 {

	private volatile int count;
	private volatile int tam;
	private volatile int ini;
	private volatile int fin;
	private volatile VolatileInteger buf[];
	
	public SyncMonitorEj13(int t) {
		ini = 0;
		fin = 0;
		buf  = new VolatileInteger[t];
		count = 0;
		tam = t;
	}
	
	
	public synchronized void entrar_producir(int c, int[] p) {
		while(count + c > tam) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i = 0; i < c; i++) {			
			buf[fin] = new VolatileInteger(p[i]);
			fin = (fin + 1) % tam;
			count++;
		}
		notifyAll();
	}
	
	public synchronized int[] entrar_consumir(int c) {
		while(count - c < 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int[] p = new int[c];
		for(int i = 0; i < c; i++) {			
			VolatileInteger v = buf[ini];
			buf[ini] = null;
			ini = (ini+1) % tam;
			p[i] = v.intValue();
			count--;
		}	
		notifyAll();
		return p;
	}
}

