import java.util.ArrayList;
import java.util.List;

public class practica2ej2 {

	volatile public int cont;
	
	public practica2ej2( ) { cont = 0; }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n_procesos = Integer.parseInt(args[0]);
		int n_acciones = Integer.parseInt(args[1]);
		int lock_type = Integer.parseInt(args[2]);
		Lock lk;
//		if (lock_type == 1) {
//			lk = new LockRompeEmpate(n_procesos*2);
//		}
//		else if(lock_type == 2) {
			lk = new LockBakery(n_procesos*2);
//		}
//		else {
//			lk = new LockTicket(n_procesos*2);
//		}
		practica2ej2 p12 = new practica2ej2();
		p12.execute(n_procesos, n_acciones, lk);
	}
	
	public void execute(int procesos, int acciones, Lock l) {
		List<prcThread> lThreads = new ArrayList<prcThread>();
		for(int i = 1; i < procesos+1; i++) {
			lThreads.add(new incThread(acciones,i, l));
			lThreads.add(new decThread(acciones,i+procesos,l));
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
		protected Lock lk;
		
		public prcThread (int id, Lock l) {
			this.id = id;
			lk = l;
		}
		
		public int ident() {
			return id;
		}
		
	}
	
	
	public class decThread extends prcThread {
		int a;
		public decThread (int acciones, int id, Lock lock) { super(id, lock); a = acciones;	}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				lk.takeLock(id);
				cont--;	
				lk.releaseLock(id);
			}
		}
	}
	
	public class incThread extends prcThread {
		int a;
		public incThread (int acciones, int id, Lock lock) { super(id, lock); a = acciones;	}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				lk.takeLock(id);
				cont++;	
				lk.releaseLock(id);
			}
		}
	}
}
