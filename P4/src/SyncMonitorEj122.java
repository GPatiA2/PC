
public class SyncMonitorEj122 {

	private volatile int count;
	private volatile int tam;
	private volatile int ini;
	private volatile int fin;
	private volatile VolatileInteger buf[];
	
	public SyncMonitorEj122(int t) {
		ini = 0;
		fin = 0;
		buf  = new VolatileInteger[t];
		count = 0;
		tam = t;
	}
	
	
	public synchronized void entrar_producir(int p) {
		while(count == tam) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized int entrar_consumir() {
		while(count == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
}

