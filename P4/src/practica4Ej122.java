import java.util.ArrayList;
import java.util.List;

public class practica4Ej122 {

	
	int productores;
	int consumidores;
	Almacen a;
	List<Thread> l;
	volatile SyncMonitorEj122 m;
	
	public practica4Ej122(int p, int c, int tam) {
		productores = p;
		consumidores = c;
		l = new ArrayList<Thread>();
		m = new SyncMonitorEj122(tam);
	}
	
	public static void main(String[] args) {
		int productores = Integer.parseInt(args[0]);
		int consumidores = Integer.parseInt(args[1]);
		int acciones = Integer.parseInt(args[2]);
		int tamanio = Integer.parseInt(args[3]);
		practica4Ej122 p32 = new practica4Ej122(productores, consumidores, tamanio); 
		p32.execute(productores, consumidores, acciones);
	}

	public void execute(int productores, int consumidores, int acciones) {
		for(int i = 0; i < productores; i++) {
			l.add(new prodThread(m,acciones));
		}
		int aCons = (int)Math.ceil(Double.valueOf((acciones*productores))/consumidores);
		System.out.println(aCons);
		for(int i = 0; i < consumidores; i++) {
			l.add(new consThread(m,aCons));
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
		SyncMonitorEj122 m;
		int v;
		
		public prodThread(SyncMonitorEj122 m, int v) {
			this.m = m;
			c = 0;
			this.v = v;
		}
		
		public void run() {
			for(c = 0; c < v; c++) {
				m.entrar_producir(c);
			}
		}
	}
	
	public class consThread extends Thread{
		SyncMonitorEj122 m;
		int v;
		public consThread(SyncMonitorEj122 m, int v) {
			this.m = m;
			this.v = v;
		}
		
		public void run() {
			for(int i = 0; i < v; i++) {
				int cons = m.entrar_consumir();
				System.out.println(cons);
				
			}
		}
	}

}
