import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class practica3ej2 {

	
	int productores;
	int consumidores;
	Almacen a;
	List<Thread> l;
	
	public practica3ej2(int p, int c) {
		productores = p;
		consumidores = c;
		l = new ArrayList<Thread>();
		a = new AlmacenPractica();
		
	}
	
	public static void main(String[] args) {
		int productores = Integer.parseInt(args[0]);
		int consumidores = Integer.parseInt(args[1]);
		int acciones = Integer.parseInt(args[2]);
		practica3ej2 p32 = new practica3ej2(productores, consumidores); 
		p32.execute(productores, consumidores, acciones);
	}

	public void execute(int productores, int consumidores, int acciones) {
		for(int i = 0; i < productores; i++) {
			l.add(new prodThread(a,acciones));
		}
		for(int i = 0; i < consumidores; i++) {
			l.add(new consThread(a,acciones));
		}
		for(int i = 0; i < l.size(); i++) {
			l.get(i).start();
		}
		for(int i = 0; i < l.size(); i++) {
			try {
				l.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class prodThread extends Thread{
		
		int c;
		Almacen a;
		int v;
		
		public prodThread(Almacen a, int v) {
			this.a = a;
			c = 0;
			this.v = v;
		}
		
		public void run() {
			for(c = 0; c < v; c++) {
				a.almacenar(c);
			}
		}
	}
	
	public class consThread extends Thread{
		Almacen a;
		int v;
		public consThread(Almacen al, int v) {
			a = al;
			this.v = v;
		}
		
		public void run() {
			for(int i = 0; i < v; i++) {
				int cons = a.extraer();
				System.out.println(cons);
			}
		}
	}
	
	public class AlmacenPractica implements Almacen {
		Semaphore empty; // Espacio disponible en el buffer
		Semaphore full;  // Cantidad de elementos en el buffer
		Semaphore mutexP; // Para tener un solo productor a la vez
		Semaphore mutexC; // Para tener un solo consumidor a la vez
		volatile Integer buf;
		
		public AlmacenPractica() {
			buf = null;
			full = new Semaphore(0);
			empty = new Semaphore(1);
			mutexP = new Semaphore(1);
			mutexC = new Semaphore(1);
		}
		
		@Override
		public void almacenar(int producto) {
			// TODO Auto-generated method stub
			try {
				empty.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mutexP.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buf = Integer.valueOf(producto);
			mutexP.release();
			full.release();
		}

		@Override
		public int extraer() {
			// TODO Auto-generated method stub
			try {
				full.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mutexC.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int v = buf.intValue();
			buf = null;
			mutexC.release();
			empty.release();
			return v;
		}

		@Override
		public boolean hayProducto() {
			// TODO Auto-generated method stub
			return buf != null;
		}

		@Override
		public int getTam() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
	}
}
