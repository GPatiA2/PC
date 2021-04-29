import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class practica4Ej11 {

	volatile SyncMonitorEj11 m1;
	
	public practica4Ej11( ) { 
		m1 = new SyncMonitorEj11();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n_procesos = Integer.parseInt(args[0]);
		int n_acciones = Integer.parseInt(args[1]);
		practica4Ej11 p12 = new practica4Ej11();
		p12.execute(n_procesos, n_acciones);
	}
	
	public void execute(int procesos, int acciones) {
		List<prcThread> lThreads = new ArrayList<prcThread>();
		for(int i = 0; i < procesos; i++) {
			lThreads.add(new incThread(acciones, i+2, m1));
			lThreads.add(new decThread(acciones, i+1, m1));
		}
		for(prcThread t : lThreads) {
			t.start();
		}
		for(prcThread t : lThreads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(m1.getC());
	}
	
	
	
	public class prcThread extends Thread {
		
		protected int id;
		
		public prcThread (int id) {
			this.id = id;
		}
		
		public int ident() {
			return id;
		}
		
	}
	
	
	public class decThread extends prcThread {
		int a;
		SyncMonitorEj11 m;
		public decThread (int acciones, int id, SyncMonitorEj11 m1) { super(id); a = acciones;	m = m1;}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				m.dec();
			}
		}
	}
	
	public class incThread extends prcThread {
		int a;
		SyncMonitorEj11 m;
		
		public incThread (int acciones, int id, SyncMonitorEj11 m1) { super(id); a = acciones;	m = m1;}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				m.inc();
			}
		}
	}
}