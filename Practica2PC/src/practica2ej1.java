import java.util.ArrayList;
import java.util.List;

public class practica2ej1 {

	volatile public int cont;
	volatile public boolean in1 = false;
	volatile public boolean in2 = false;
	volatile public int last = 0;
	
	public practica2ej1( ) { cont = 0; }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n_procesos = Integer.parseInt(args[0]);
		int n_acciones = Integer.parseInt(args[1]);
		practica2ej1 p12 = new practica2ej1();
		p12.execute(1, n_acciones);
	}
	
	public void execute(int procesos, int acciones) {
		List<prcThread> lThreads = new ArrayList<prcThread>();
		for(int i = 0; i < procesos; i++) {
			lThreads.add(new incThread(acciones, i+2));
			lThreads.add(new decThread(acciones, i+1));
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
		public decThread (int acciones, int id) { super(id); a = acciones;	}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				in1 = true;
				last = id;
				while(in2 && last != 2) {}
				
				cont--;	
				
				in1 = false;
				
			}
		}
	}
	
	public class incThread extends prcThread {
		int a;
		public incThread (int acciones, int id) { super(id); a = acciones;	}
		
		public void run() {
			for(int i = 0; i < a ; i++) { 
				in2 = true;
				last = id;
				while(in1 && last != 1) {}
				
				cont++;
				
				in2 = false;
			}
		}
	}
}
