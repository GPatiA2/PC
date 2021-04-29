
public class SyncMonitorEj12 {

	private volatile int count;
	private volatile int tam;
	
	public SyncMonitorEj12(int t) {

		count = 0;
		tam = t;
	}
	
	
	public synchronized void entrar_producir() {
		while(count == tam) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void salir_producir() {
		count = count + 1;
		notifyAll();
	}
	
	public synchronized void entrar_consumir() {
		while(count == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void salir_consumir() {
		count = count - 1;
		notifyAll();
	}
}
