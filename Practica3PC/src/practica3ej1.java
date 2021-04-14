import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class practica3ej1 {

	volatile public int cont;
	volatile public Semaphore sem;
	
	public practica3ej1( ) { cont = 0; sem = new Semaphore(1); }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n_procesos = Integer.parseInt(args[0]);
		int n_acciones = Integer.parseInt(args[1]);
		practica3ej1 p12 = new practica3ej1();
		p12.execute(n_procesos, n_acciones);
	}
	
	public void execute(int procesos, int acciones) {
		List<prcThread> lThreads = new ArrayList<prcThread>();
		for(int i = 0; i < procesos; i++) {
			lThreads.add(new incThread(acciones, i+2, sem));
			lThreads.add(new decThread(acciones, i+1, sem));
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
		System.out.println(cont);
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
		Semaphore s;
		public decThread (int acciones, int id, Semaphore s) { super(id); a = acciones;	this.s = s;}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				try {
					s.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cont--;	
				s.release();
				
			}
		}
	}
	
	public class incThread extends prcThread {
		int a;
		Semaphore s;
		public incThread (int acciones, int id, Semaphore s) { super(id); a = acciones;	this.s = s;}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
	
				try {
					s.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cont++;
				s.release();
			}
		}
	}
}