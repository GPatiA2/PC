import java.util.ArrayList;
import java.util.List;

public class practica1ej2 {

	public int cont;
	
	public practica1ej2( ) { cont = 0; }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n_procesos = Integer.parseInt(args[0]);
		int n_acciones = Integer.parseInt(args[1]);
		practica1ej2 p12 = new practica1ej2();
		p12.execute(n_procesos, n_acciones);
	}
	
	public void execute(int procesos, int acciones) {
		List<Thread> lThreads = new ArrayList<Thread>();
		for(int i = 0; i < procesos; i++) {
			lThreads.add(new decThread(acciones));
			lThreads.add(new incThread(acciones));
		}
		for(Thread t : lThreads) {
			t.start();
		}
		
		System.out.println(cont);
	}
	
	public class decThread extends Thread {
		int a;
		public decThread (int acciones) { a = acciones;	}
		
		public void run() {
			for(int i = 0; i < a ; i++) { cont--;}
		}
	}
	
	public class incThread extends Thread {
		int a;
		public incThread (int acciones) { a = acciones;	}
		
		public void run() {
			for(int i = 0; i < a ; i++) { cont++;}
		}
	}
}
